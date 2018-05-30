package edu.handong.csee.java.HW3;

public class Message {
	String name;
	String time;
	String line;
	
	public Message(String n, String t, String l) {
		// TODO Auto-generated constructor stub
		this.name = n;
		this.time = t;
		this.line = l;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
}
