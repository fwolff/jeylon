package test;

import org.jeylon.serial.Deconstructed;
import org.jeylon.serial.impl.AttributeImpl;

public class MyBean {
	
	private String name;
	private String nick;
	private MyBean other;
	
	public MyBean(String name, String nick, MyBean other) {
		this.name = name;
		this.nick = nick;
		this.other = other;
	}

	public MyBean(Deconstructed deconstructed) {
		this.name = (String)deconstructed.get(new AttributeImpl(MyBean.class, "name"));
		this.nick = (String)deconstructed.get(new AttributeImpl(MyBean.class, "nick"));
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}

	public MyBean getOther() {
		return other;
	}
	public void setOther(MyBean other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return getClass().getName() + " {name=" + name + ", nick=" + nick + "}";
	}
}
