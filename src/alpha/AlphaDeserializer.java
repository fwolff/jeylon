package alpha;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

import org.jeylon.serial.DeserializationContext;
import org.jeylon.serial.Reference;
import org.jeylon.serial.StatefulReference;
import org.jeylon.serial.impl.AttributeImpl;
import org.jeylon.serial.impl.DeconstructedImpl;
import org.jeylon.serial.impl.DeserializationContextImpl;

public class AlphaDeserializer implements Alpha {

	private final DataInput in;
	private final DeserializationContext context;

	private final List<String> stringReferences;
	
	private int referenceIndex = 0;

	public AlphaDeserializer(DataInput in) {
		this.in = in;
		this.context = new DeserializationContextImpl();
		this.stringReferences = new ArrayList<String>();
	}

	public Object read() throws Exception {
		Object referenceOrObject = readNoInstance();
		if (referenceOrObject instanceof StatefulReference)
			return ((StatefulReference)referenceOrObject).instance();
		return referenceOrObject;
	}
	
	private Object readNoInstance() throws Exception {
		switch (in.readByte()) {
		case STRING_TYPE:
			return readString();
		case OBJECT_TYPE:
			return readObject();
		default:
			throw new RuntimeException("Huh...");
		}
	}
	
	private String readString() throws Exception {
		switch (in.readByte()) {
		case REFERENCE_TYPE:
			int ref = in.readInt();
			return stringReferences.get(ref);
		case PLAIN_TYPE:
			String s = in.readUTF();
			stringReferences.add(s);
			return s;
		default:
			throw new RuntimeException("Huh...");
		}
	}
	
	private Object readObject() throws Exception {
		int type = in.readByte();
		
		if (type == REFERENCE_TYPE) {
			int ref = in.readInt();
			return context.reference(ref);
		}
		if (type == PLAIN_TYPE) {
			String className = (String)readNoInstance();
			Class<?> cls = context.forName(className);
			
			Reference reference = context.reference(referenceIndex++);
			DeconstructedImpl deconstructed = new DeconstructedImpl(cls);
			reference = reference.deserialize(deconstructed);

			int count = in.readInt();
			for (int i = 0; i < count; i++) {
				String name = (String)readNoInstance();
				Object value = readNoInstance();
				deconstructed.add(new AttributeImpl(cls, name), value);
			}
			
			return reference;
		}
		throw new RuntimeException("Huh...");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(context.toString());
		
		sb.append("String references: {\n");
		for (int i = 0; i < stringReferences.size(); i++)
			sb.append("  ").append(i).append(": ").append(stringReferences.get(i)).append('\n');
		sb.append("}\n");
		
		return sb.toString();
	}
}
