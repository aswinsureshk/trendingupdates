package com.misonet.model.mao;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.misonet.model.UserProfile;
import com.misonet.utils.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@Repository
public class UserProfileMaoImpl implements IUserProfileMao {
	
	
//    @Autowired
//    @Qualifier("MongoTemplate")
//    private MongoOperations mongoOperations;
	
	static MongoConnection mongoConnection = new MongoConnection();
	
	
	private Document getUserProfileDocument(UserProfile userProfile) {
		
		Document document = new Document();
		document.append("name", userProfile.getName());
		document.append("interests", userProfile.getInterests());;
		document.append("email", userProfile.getEmail());
		document.append("password", userProfile.getPassword());
		document.append("preferedDis", userProfile.getPreferedDis());
		
		return document;
		
	}

	@Override
	public String insertNewUser(UserProfile userProfile) {
		

		MongoClientURI uri = new MongoClientURI(mongoConnection.MongoUrl());
		MongoClient mongoClient = null;
		mongoClient = new MongoClient(uri);
		mongoClient.getReplicaSetStatus();
		
		MongoDatabase m = mongoClient.getDatabase("swift");
	 
		MongoCollection<Document> c = m.getCollection("users");
	 
	 
		Document doc = getUserProfileDocument(userProfile);
	
		c.insertOne(doc);
	 
		mongoClient.close();
		
		return doc.get("_id").toString();
	}
	
	

	@Override
	public void getFeed(String userid) {
//		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(userid));
//        List<UserProfile> userProfiles = mongoOperations.find(query, UserProfile.class);
////        if(userProfiles != null && userProfiles.isEmpty()) {
////        	List<EventClass.java> list
////        }else {
////        	return null;
////        }
//		
//	}
	}

	@Override
	public String login(String email, String password) {
		MongoClientURI uri = new MongoClientURI(mongoConnection.MongoUrl());
		MongoClient mongoClient = null;
		mongoClient = new MongoClient(uri);
		mongoClient.getReplicaSetStatus();
		
		MongoDatabase m = mongoClient.getDatabase("swift");
	 
		MongoCollection<Document> c = m.getCollection("users");
		
		Document d = c.find().filter(Filters.eq("email",email)).first();
		
		if(d.get("password").equals(password)) {
			return d.get("_id").toString();
		}else {
			return null;
		}
		
		
	}
	

}
