package org.jeylon.serial;

public interface Deconstructed extends Iterable<Deconstructed.AttributeValue> {

	public interface AttributeValue {
		
		Attribute getAttribute();
		Object getValue();
	}
	
	Class<?> getDeclaringClass();
	
	int length();
	
	Object get(Attribute attribute);
}
