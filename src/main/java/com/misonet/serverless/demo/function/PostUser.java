package com.misonet.serverless.demo.function;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.misonet.interceptor.UserValidatorClass;
import com.misonet.model.ApiResponse;
import com.misonet.model.UserProfile;
import com.misonet.model.mao.UserProfileMaoImpl;
import com.misonet.serverless.demo.model.ServerlessInput;
import com.misonet.serverless.demo.model.ServerlessOutput;

/**
 * Lambda function that triggered by the API Gateway event "POST /". 
 */
public class PostUser implements RequestHandler<ServerlessInput, ServerlessOutput> {

	@Override
    public ServerlessOutput handleRequest(ServerlessInput serverlessInput, Context context) {
		ServerlessOutput output = new ServerlessOutput();

            try {
            	  UserProfileMaoImpl umaImpl = new UserProfileMaoImpl();
            	  UserProfile up = new UserProfile();
            	  up.setName(serverlessInput.getQueryStringParameters().get("name"));
            	  List<String> up_interests = new ArrayList<>();
            	  
            	  if (serverlessInput.getQueryStringParameters().get("interests") != null)
	            	  for (String s : serverlessInput.getQueryStringParameters().get("interests").split(","))
	            		  up_interests.add(s);
            	  
            	  up.setInterests(up_interests);
                  String id_inserted = umaImpl.insertNewUser(up);
                  output.setStatusCode(200);
                  output.setBody("User with id : "+ id_inserted);
                  output.setHeaders(UserValidatorClass.getResponseHeaders());
                 
            } catch (Exception e) {
                output.setStatusCode(500);
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                output.setBody(sw.toString());
            }
        return output;
    }

}