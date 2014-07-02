package org.jeylon.serial.impl;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.Deconstructed.AttributeValue;
import org.jeylon.serial.StatefulReference;

public class StatefulReferenceImpl implements StatefulReference {

	private final Integer id;
	private final Deconstructed deconstructed;
	private Object instance = null;
	
	public StatefulReferenceImpl(Integer id, Deconstructed deconstructed) {
		this.id = id;
		this.deconstructed = deconstructed;
	}

	@Override
	public Integer id() {
		return id;
	}

	@Override
	public StatefulReference deserialize(Deconstructed deconstructed) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object instance() {
		if (instance == null) {
			try {
				Object[] params = new Object[deconstructed.length()];
				int i = 0;
				for (AttributeValue param : deconstructed) {
					Object value = param.getValue();
					if (value instanceof StatefulReference)
						params[i++] = ((StatefulReference)value).instance();
					else
						params[i++] = param.getValue();
				}
				instance = deconstructed.getDeclaringClass().getConstructors()[0].newInstance(params);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	@Override
	public String toString() {
		return "StatefulReference: " + deconstructed;
	}
}
