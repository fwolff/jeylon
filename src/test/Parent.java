package test;

import org.jeylon.serial.impl.ConstructorParams;

@ConstructorParams({"name", "child1", "child2"})
public class Parent {

	private final String name;
	private final Child child1;
	private final Child child2;

	public Parent(String name, Child child1, Child child2) {
		this.name = name;
		this.child1 = child1;
		this.child2 = child2;
	}

	public String getName() {
		return name;
	}

	public Child getChild1() {
		return child1;
	}

	public Child getChild2() {
		return child2;
	}
}
