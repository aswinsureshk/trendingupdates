package com.misonet.model;


import org.springframework.data.annotation.Id;

public class EventClass {

	@Id
	String id;
	String eventName;
	String desc;
	String location;
	String interest;
	String userid;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	@Override
	public String toString() {
		return "EventClass [eventName=" + eventName + ", desc=" + desc + ", location=" + location + ", interest="
				+ interest + "]";
	}
	public EventClass(String eventName, String desc, String location, String interest) {
		super();
		this.eventName = eventName;
		this.desc = desc;
		this.location = location;
		this.interest = interest;
	}
	
	
	
	
	
}
