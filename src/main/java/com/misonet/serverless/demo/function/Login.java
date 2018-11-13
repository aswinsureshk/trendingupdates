package com.misonet.serverless.demo.function;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misonet.exception.InvalidLoginException;
import com.misonet.interceptor.UserValidatorClass;
import com.misonet.model.mao.UserProfileMaoImpl;
import com.misonet.serverless.demo.model.ServerlessInput;
import com.misonet.serverless.demo.model.ServerlessOutput;

public class Login  implements RequestHandler<ServerlessInput, ServerlessOutput>{
	
	@Override
    public ServerlessOutput handleRequest(ServerlessInput serverlessInput, Context context) {
		ServerlessOutput output = new ServerlessOutput();

            try {
            	  UserProfileMaoImpl umaImpl = new UserProfileMaoImpl();
            	  String email = "",  password = "";
            	  String body = serverlessInput.getBody();
            	  Map<String, Object> input =  
            		        new ObjectMapper().readValue(body, HashMap.class);
            	  if (input != null) {
            		  email = (String) input.get("email");
            	      password = (String) input.get("password");
            	  }
            	  
            	  if (email == null || password == null)
            		  throw new InvalidLoginException("Login failed. Incorrect email id or password. null.");
            	  
            	  String result = umaImpl.login(email, password);
            	  
            	  if (result == null || result.isEmpty())
            		  throw new InvalidLoginException("email = " + email + " , password = " + password);

                  output.setStatusCode(200);
                  output.setBody("Login Successful");
                 
            } catch (Exception e) {
                
            	output.setStatusCode(500);
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                output.setBody(e.getMessage());
            
            } finally {
            
            	output.setHeaders(UserValidatorClass.getResponseHeaders());
            }
            return output;
	}

}
