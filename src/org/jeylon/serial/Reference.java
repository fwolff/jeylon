package org.jeylon.serial;

public interface Reference {

	Integer id();
	
	StatefulReference deserialize(Deconstructed deconstructed);
}
