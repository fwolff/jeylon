package org.jeylon.serial.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.DeserializationContext;
import org.jeylon.serial.Reference;
import org.jeylon.serial.StatefulReference;

public class DeserializationContextImpl implements DeserializationContext {

	private final Map<Integer, Reference> references = new TreeMap<Integer, Reference>();
	
	public DeserializationContextImpl() {
	}

	@Override
	public Class<?> forName(String className) throws ClassNotFoundException {
		return Class.forName(className);
	}

	@Override
	public Reference reference(int id) {
		Reference reference = references.get(id);
		if (reference == null) {
			reference = new ReferenceImpl(this, id);
			references.put(id, reference);
		}
		return reference;
	}
	
	public StatefulReference deserialize(int id, Deconstructed deconstructed) {
		Reference reference = references.get(id);
		if (reference == null || (reference instanceof StatefulReference))
			throw new RuntimeException("Bad reference: id=" + id + ", reference: " + reference);
		StatefulReference statefulReference = new StatefulReferenceImpl(id, deconstructed);
		references.put(id, statefulReference);
		return statefulReference;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Object references: {\n");
		for (Entry<Integer, Reference> entry : references.entrySet()) {
			sb.append("  ").append(entry.getKey()).append(": ").append(entry.getValue()).append('\n');
		}
		sb.append("}\n");
		return sb.toString();
	}
}
