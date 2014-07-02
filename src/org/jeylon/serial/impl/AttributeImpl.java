package org.jeylon.serial.impl;

import org.jeylon.serial.Attribute;

public class AttributeImpl implements Attribute {

	private final Class<?> declaringClass;
	private final String name;
	
	public AttributeImpl(Class<?> declaringClass, String name) {
		this.declaringClass = declaringClass;
		this.name = name;
	}

	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof AttributeImpl))
			return false;
		return name.equals(((AttributeImpl)obj).name);
	}

	@Override
	public String toString() {
		return declaringClass.getName() + "." + name;
	}
}
