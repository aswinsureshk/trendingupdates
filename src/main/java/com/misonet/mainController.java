package com.misonet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.misonet.model.ApiResponse;
import com.misonet.model.EventClass;
import com.misonet.model.FailureResponse;
import com.misonet.model.SuccessResponse;
import com.misonet.model.UserProfile;
import com.misonet.model.mao.IEventMao;
import com.misonet.model.mao.IInterestMao;
import com.misonet.model.mao.IUserProfileMao;
import com.misonet.utils.RequestContextService;

@Controller
public class mainController {
	
	
	@Autowired 
	HttpServletRequest request;
	
	@Autowired 
	private HttpServletResponse response;
	
	@Autowired
	IUserProfileMao IUserProfileMao;
	
	@Autowired
	IEventMao iEventMao;

	@Autowired
	IInterestMao iInterestMao;
	
	@Autowired
	RequestContextService requestContextService;
	


    @RequestMapping(value = "/hello", method = {RequestMethod.GET})
    public String changeKakfaTopicName(@RequestParam String name) {

        return "success";
    }
    
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ApiResponse<String> login(@RequestParam String email, @RequestParam String password,
    @RequestParam HttpServletResponse response) {
    	
    	//@RequestParam HttpServletResponse response
 
    	String userid = IUserProfileMao.login(email,password);
    	
    	ApiResponse<String> apiResponse = null;
    	if(userid != null) {
    		 apiResponse = new SuccessResponse<String>(userid,
     	            200, "OK");
    		 
    		 try {
				requestContextService.createCookie(userid,request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}else {
    		 apiResponse = new SuccessResponse<String>("",500, "OK");
    	}
        return apiResponse;
    }
    
    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public ApiResponse<String> logout(@RequestParam HttpServletResponse response) throws IOException {


    		 requestContextService.deleteCookie(response);
    		 ApiResponse<String> apiResponse = new SuccessResponse<String>("Success",
     	            200, "OK");
    		 
    		 response.sendRedirect("url to login page");
    	
        return apiResponse;
    }
    
    @RequestMapping(value = "/newUser", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse<String> addUser(@RequestParam String name,
    @RequestParam List<String> interests,@RequestParam String email, @RequestParam String password, @RequestParam int predis ){
    	
    	
    UserProfile userProfile = new UserProfile();
    userProfile.setName(name);
    userProfile.setInterests(interests);
    userProfile.setEmail(email);
    userProfile.setPassword(password);
    userProfile.setPreferedDis(predis);
    	
    String id = IUserProfileMao.insertNewUser(userProfile);
    	
    	ApiResponse<String> apiResponse = new SuccessResponse<String>(id,
                200, "OK");
     
    	return apiResponse;
    }
    
    @RequestMapping(value = "/createEvent", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse<EventClass> creatEvent(@RequestParam String name, @RequestParam String location, 
    		@RequestParam String desc, @RequestParam String interest, HttpServletRequest request){
 
    	EventClass event = new EventClass(name,desc,location,interest);
    	
    	
    	String userid = null;
		try {
			userid = requestContextService.getUserId(request);
			
	    	EventClass createdEvent = iEventMao.creatEvent(event,userid);
	    	
	    	 ApiResponse<EventClass> apiResponse = new SuccessResponse<EventClass>(createdEvent,
	    	            200, "OK");
	    	 
	    	 return apiResponse;
	    	 
		} catch (UnsupportedEncodingException e) {
	    	 ApiResponse<EventClass> apiResponse = new FailureResponse<EventClass>();
	    	 e.printStackTrace(); 
	    	 return apiResponse;
			
		}
    }
    
    @RequestMapping(value = "/getUserFeed", method = {RequestMethod.GET})
    @ResponseBody
    public ApiResponse<List<Document>> getUserFeed(HttpServletRequest request){
 
    	String userid;
		try {
			userid = requestContextService.getUserId(request);
			
			List<Document> listOfFeedDocuments =  iInterestMao.getUserFeed(userid);
			
			ApiResponse<List<Document>> apiResponse = new SuccessResponse<List<Document>>(listOfFeedDocuments,
    	            200, "OK");
    	 
    	 return apiResponse;
			
		} catch (UnsupportedEncodingException e) {
			 ApiResponse<List<Document>> apiResponse = new FailureResponse<List<Document>>();
	    	 e.printStackTrace(); 
	    	 return apiResponse;
		}
    	
    	
    }
}
