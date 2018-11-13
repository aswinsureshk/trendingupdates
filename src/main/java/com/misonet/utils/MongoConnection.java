package com.misonet.utils;

import org.springframework.stereotype.Repository;

@Repository
public class MongoConnection {
	
	String mongoUrl = "mongodb+srv://mongodb:mongodb@cluster0-qlnsf.mongodb.net";
	
	public String MongoUrl() {
		return mongoUrl;
	}

}
