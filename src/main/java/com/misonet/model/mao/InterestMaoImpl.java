package com.misonet.model.mao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.misonet.model.InterestClass;
import com.misonet.utils.MongoConnection;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Repository
public class InterestMaoImpl implements IInterestMao{
	
	static MongoConnection mongoConnection = new MongoConnection();

	@SuppressWarnings("resource")
	@Override
	public List<Document> getUserFeed(String userid) {
		
		List<Document> listOfDocument = new ArrayList<Document>();
		
		MongoClientURI uri = new MongoClientURI(mongoConnection.MongoUrl());
		MongoClient mongoClient = null;
		mongoClient = new MongoClient(uri);
		mongoClient.getReplicaSetStatus();
		
		MongoDatabase m = mongoClient.getDatabase("swift");
	 
		MongoCollection<Document> c_users = m.getCollection("users");
		MongoCollection<Document> c_events = m.getCollection("events");
		
		Document userProfile = c_users.find().filter(Filters.eq("_id",new ObjectId(userid))).first();
		
		if(userProfile != null) {

			@SuppressWarnings("unchecked")
			List<String> listOfInterest = (List<String>) userProfile.get("interests");
			
			//Filters f = Filters.and(Filters.in("interest", listOfInterest),Filters.lte(findDist("location"), userDoc.get("preferedDis")));
			
			Bson filt = Filters.in("interest", listOfInterest);
			
			FindIterable<Document> documents = c_events.find(filt).sort(new BasicDBObject("_id",-1)).limit(20);

			for(Document d:documents) {
				listOfDocument.add(d);
			}
		}
	
		return listOfDocument;
	}

}
