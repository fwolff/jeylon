package test;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import alpha.AlphaDeserializer;
import alpha.AlphaSerializer;

public class TestAlpha {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		Child child = new Child("John Doo Jr", "John Doo");
		Parent parent = new Parent("John Doo", child, child);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		AlphaSerializer ser = new AlphaSerializer(new DataOutputStream(baos));
		ser.write(parent);
		
		System.out.println("== Serializer state ==");
		System.out.println();
		System.out.println(ser);
		
		AlphaDeserializer des = new AlphaDeserializer(new DataInputStream(new ByteArrayInputStream(baos.toByteArray())));
		Parent copy = (Parent)des.read();
		
		assertEquals(parent.getName(), copy.getName());
		assertEquals(child.getName(), copy.getChild1().getName());
		assertTrue(copy.getName() == copy.getChild1().getParentName());
		assertTrue(copy.getChild1() == copy.getChild2());
		
		System.out.println(" == Deserializer state ==");
		System.out.println();
		System.out.println(des);
	}
}
