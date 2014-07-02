package org.jeylon.serial.impl;

import java.util.ArrayList;

import org.jeylon.serial.Attribute;
import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.Deconstructed.AttributeValue;
import org.jeylon.serial.Reference;

public class DeconstructedImpl extends ArrayList<AttributeValue> implements Deconstructed {

	private static final long serialVersionUID = 1L;
	
	public static class AttributeValueImpl implements AttributeValue {

		private final Attribute attribute;
		private final Object value;
		
		public AttributeValueImpl(Attribute attribute, Object value) {
			this.attribute = attribute;
			this.value = value;
		}

		@Override
		public Attribute getAttribute() {
			return attribute;
		}

		@Override
		public Object getValue() {
			return value;
		}

		@Override
		public String toString() {
			return attribute.getName() + "=" + (value instanceof Reference ? "Reference@" + ((Reference)value).id() : value);
		}
	}
	
	private final Class<?> cls;

	public DeconstructedImpl(Class<?> cls) {
		this.cls = cls;
	}
	
	@Override
	public Class<?> getDeclaringClass() {
		return cls;
	}

	@Override
	public int length() {
		return size();
	}

	public void add(Attribute attribute, Object value) {
		add(new AttributeValueImpl(attribute, value));
	}

	@Override
	public Object get(Attribute attribute) {
		final int size = size();
		for (int i = 0; i < size; i++) {
			if (get(i).getAttribute().equals(attribute))
				return get(i).getValue();
		}
		throw new RuntimeException("Attribute not found: " + attribute);
	}
}
