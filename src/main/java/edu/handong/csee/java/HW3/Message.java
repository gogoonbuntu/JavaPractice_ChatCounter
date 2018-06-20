package edu.handong.csee.java.HW3;

public class Message {
	String name;
	String date;
	String time;
	String line;
	String file;
	
	public Message(String n, String d, String t, String l) {
		// TODO Auto-generated constructor stub
		this.name = n;
		this.date = d;
		this.time = t;
		this.line = l;
		//this.file = f;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		String dateTime = date+", "+time;
		return dateTime;
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
