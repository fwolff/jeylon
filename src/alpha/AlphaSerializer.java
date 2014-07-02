package alpha;

import java.io.DataOutput;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.Deconstructed.AttributeValue;
import org.jeylon.serial.SerializationContext;
import org.jeylon.serial.impl.SerializationContextImpl;

public class AlphaSerializer implements Alpha {

	private final DataOutput out;
	private final SerializationContext context;
	private final Map<Object, Integer> objectReferences;
	private final Map<String, Integer> stringReferences;

	public AlphaSerializer(DataOutput out) {
		this.out = out;
		this.context = new SerializationContextImpl();
		this.objectReferences = new IdentityHashMap<Object, Integer>();
		this.stringReferences = new HashMap<String, Integer>();
	}

	public void write(Object o) throws Exception {
		if (o instanceof String)
			writeString((String)o);
		else
			writeObject(o);
	}
	
	private void writeString(String s) throws Exception {
		out.writeByte(STRING_TYPE);

		Integer ref = stringReferences.get(s);
		if (ref != null) {
			out.writeByte(REFERENCE_TYPE);
			out.writeInt(ref);
		}
		else {
			stringReferences.put(s, stringReferences.size());
			out.writeByte(PLAIN_TYPE);
			out.writeUTF(s);
		}
	}
	
	private void writeObject(Object o) throws Exception {
		out.writeByte(OBJECT_TYPE);

		Integer ref = objectReferences.get(o);
		if (ref != null) {
			out.writeByte(REFERENCE_TYPE);
			out.writeInt(ref);
		}
		else {
			objectReferences.put(o, objectReferences.size());
			out.writeByte(PLAIN_TYPE);
			writeString(o.getClass().getName());
			
			Deconstructed deconstructed = context.deconstruct(o);
			out.writeInt(deconstructed.length());
			for (AttributeValue attributeValue : deconstructed) {
				writeString(attributeValue.getAttribute().getName());
				write(attributeValue.getValue());
			}
		}
	}

	@Override
	public String toString() {
		Comparator<Object> comp = new Comparator<Object>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(Object o1, Object o2) {
				return ((Entry<?, Integer>)o1).getValue().compareTo(((Entry<?, Integer>)o2).getValue());
			}
		};
		
		StringBuilder sb = new StringBuilder();
		
		Entry<?, ?>[] refs = objectReferences.entrySet().toArray(new Entry[0]);
		Arrays.sort(refs, comp);
		sb.append("Object references: {\n");
		for (Entry<?, ?> entry : refs)
			sb.append("  ").append(entry.getValue()).append(": ").append(entry.getKey()).append('\n');
		sb.append("}\n");
		
		refs = stringReferences.entrySet().toArray(new Entry[0]);
		Arrays.sort(refs, comp);
		sb.append("String references: {\n");
		for (Entry<?, ?> entry : refs)
			sb.append("  ").append(entry.getValue()).append(": ").append(entry.getKey()).append('\n');
		sb.append("}\n");
		
		return sb.toString();
	}
}
