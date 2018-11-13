package com.misonet.model.mao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.misonet.model.EventClass;
import com.misonet.model.InterestClass;
import com.misonet.model.UserProfile;
import com.misonet.utils.MongoConnection;
import com.mongodb.DBObjectCodec;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import MyCodec.EventClassListCodec;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;

@Repository
public class EventMaoImpl implements IEventMao{

	
	static MongoConnection mongoConnection = new MongoConnection();
	
	private Document createEventDoc(EventClass event) {
		Document document = new Document();
		document.append("eventName", event.getEventName());
		document.append("desc", event.getDesc());
		document.append("location", event.getLocation());
		document.append("interest", event.getInterest());
		
		return document;
	}
	
	@Override
	public EventClass creatEvent(EventClass event, String userid) {
		
		MongoClientURI uri = new MongoClientURI(mongoConnection.MongoUrl());
		MongoClient mongoClient = null;
		mongoClient = new MongoClient(uri);
		mongoClient.getReplicaSetStatus();

		
		MongoDatabase m = mongoClient.getDatabase("swift");
		
		
		
		//CodecRegistry registry = CodecRegistries.fromCodecs(new EventClassListCodec());
		
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		m = m.withCodecRegistry(pojoCodecRegistry);
	
		MongoCollection<EventClass> c_events = m.getCollection("events",EventClass.class);
		
		
		MongoCollection<UserProfile> c_users = m.getCollection("users",UserProfile.class);
		


		
		MongoCollection<InterestClass> c_interests = m.getCollection("interests",InterestClass.class);
		
		
		Document doc = createEventDoc(event);
		
		c_events.insertOne(event);
		
		
//		 FindIterable<UserProfile> list1 = c_users.find().filter(FI)
//		 
//		 UserProfile userProfile = list1.firs
//		
//		if(userProfile != null) {
//			
//			List<EventClass> listOfEvnets = userProfile.getEvents();
//			
//			if(listOfEvnets != null) {
//				
//				listOfEvnets.add(event);
//				
//				userProfile.setEvents(listOfEvnets);
//				
//				c_users.findOneAndReplace(Filters.eq("_id",new ObjectId(userid)), userProfile);
//
//			}else {
//				
//				List<EventClass> newListOfEvents = new ArrayList<EventClass>();
//				
//				newListOfEvents.add(event);
//				
//				userProfile.setEvents(newListOfEvents);
//
//				
//				c_users.findOneAndReplace(Filters.eq("_id",new ObjectId(userid)), userProfile);
//			}
//		}
		
//		Document interetPage = c_interests.find().filter(Filters.eq("interestName",event.getInterest())).first();
//		
//		if(interetPage != null) {
//			
//			List<EventClass> listOfEvnets = (List<EventClass>) interetPage.get("events");
//			
//			if(listOfEvnets != null) {
//				
//				listOfEvnets.add(event);
//				
//				interetPage.replace("events", listOfEvnets);
//				
//				c_interests.findOneAndReplace(new Document("interest",event.getInterest()), interetPage);
//			}else {
//				
//				List<EventClass> newListOfEvents = new ArrayList<EventClass>();
//				newListOfEvents.add(event);
//				interetPage.replace("events", newListOfEvents);
//				
//				c_interests.findOneAndReplace(new Document("interest",event.getInterest()), interetPage);
//			}
//		}
		
		return event;
	}

}
