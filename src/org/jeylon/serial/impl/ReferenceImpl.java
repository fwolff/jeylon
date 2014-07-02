package org.jeylon.serial.impl;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.Reference;
import org.jeylon.serial.StatefulReference;

public class ReferenceImpl implements Reference {

	private final DeserializationContextImpl context;
	private final Integer id;
	
	public ReferenceImpl(DeserializationContextImpl context, Integer id) {
		this.context = context;
		this.id = id;
	}

	@Override
	public Integer id() {
		return id;
	}

	@Override
	public StatefulReference deserialize(Deconstructed deconstructed) {
		return context.deserialize(id, deconstructed);
	}
}
