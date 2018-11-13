package com.misonet.model.mao;

import java.util.List;

import org.bson.Document;

public interface IInterestMao {

	List<Document> getUserFeed(String userid);

}
