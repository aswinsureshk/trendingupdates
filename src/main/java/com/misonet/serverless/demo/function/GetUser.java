package com.misonet.serverless.demo.function;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.misonet.interceptor.UserValidatorClass;
import com.misonet.serverless.demo.model.ServerlessInput;
import com.misonet.serverless.demo.model.ServerlessOutput;

/**
 * Lambda function that triggered by the API Gateway event "GET /". 
 */

public class GetUser implements RequestHandler<ServerlessInput, ServerlessOutput> {
    @Override
    public ServerlessOutput handleRequest(ServerlessInput serverlessInput, Context context) {
    	
    	ServerlessOutput output = new ServerlessOutput();
        
        try {
            
//            UserProfileMaoImpl umaImpl = new UserProfileMaoImpl();
//            UserProfile userProfile = new UserProfile();
//            userProfile.setName(serverlessInput.getQueryStringParameters().get("name"));
            output.setStatusCode(200);
            output.setBody("No GET APIs yet");
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