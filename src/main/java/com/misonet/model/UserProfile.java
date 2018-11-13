package com.misonet.model;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


public class UserProfile {

    @Id
    ObjectId id;
    String name;
    List<String> interests;
    //List<String> events;
    int preferedDis;
    String password;
    String email;
    

    public int getPreferedDis() {
		return preferedDis;
	}

	public void setPreferedDis(int preferedDis) {
		this.preferedDis = preferedDis;
	}



    public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}



//	public List<String> getEvents() {
//		return events;
//	}
//
//	public void setEvents(List<String> events) {
//		this.events = events;
//	}

	public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
