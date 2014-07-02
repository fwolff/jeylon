package test;

import org.jeylon.serial.impl.ConstructorParams;

@ConstructorParams({"name", "parentName"})
public class Child {

	private final String name;
	private final String parentName;

	public Child(String name, String parentName) {
		this.name = name;
		this.parentName = parentName;
	}

	public String getName() {
		return name;
	}

	public String getParentName() {
		return parentName;
	}
}
