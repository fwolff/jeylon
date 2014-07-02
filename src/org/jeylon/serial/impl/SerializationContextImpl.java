package org.jeylon.serial.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.impl.DeconstructedImpl.AttributeValueImpl;
import org.jeylon.serial.SerializationContext;

public class SerializationContextImpl implements SerializationContext {

	public SerializationContextImpl() {
	}

	@Override
	public Deconstructed deconstruct(Object bean) {
		Class<?> cls = bean.getClass();
		DeconstructedImpl deconstructed = new DeconstructedImpl(cls);
		ConstructorParams paramNames = cls.getAnnotation(ConstructorParams.class);
		
		try {
			PropertyDescriptor[] descs = Introspector.getBeanInfo(cls, Object.class).getPropertyDescriptors();
			
			for (String paramName : paramNames.value()) {
				for (PropertyDescriptor desc : descs) {
					if (desc.getName().equals(paramName)) {
						deconstructed.add(new AttributeValueImpl(
							new AttributeImpl(cls, desc.getName()),
							desc.getReadMethod().invoke(bean)
						));
						break;
					}
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return deconstructed;
	}
}
