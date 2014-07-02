package org.jeylon.serial;

public interface DeserializationContext {

	Class<?> forName(String className) throws ClassNotFoundException;
	
	Reference reference(int id);
}
