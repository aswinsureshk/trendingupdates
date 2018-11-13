package com.misonet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppllcationShell {
    
	private static final String SUFFIX = "/";
	private static final String API_KEY = "AIzaSyBoCc7CQ197jxhNQsY01llgarrLRNNxgNI";
	private static final String TIMESTAMP = "1537075838";
	
	public static void main(String[] args) {
		
		String target = "San+Francisco";
		String origin = getOrigin();
		System.out.println("Origin="+ origin);
		int distance = getDistance(origin, target);
		System.out.println("Final distance in metres == " + distance);
	}
	
	private static String getOrigin() {
		
		HttpPost getRequest = new HttpPost("https://www.googleapis.com/geolocation/v1/geolocate?key=" + API_KEY);
		getRequest.addHeader("accept", "application/json");
		String response = executeRequest(getRequest);
		Map<String, Object> latlong =  null;
		try {
			
			latlong = new ObjectMapper().readValue(response, HashMap.class);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String latitude = String.valueOf(((Map)latlong.get("location")).get("lat"));
		String longitude = String.valueOf(((Map)latlong.get("location")).get("lng"));
		
		System.out.println(latitude + " " + longitude);
		
		HttpPost getRequest2 = new HttpPost("https://maps.googleapis.com/maps/api/timezone/json?location="+latitude+","+longitude+"&timestamp=" + TIMESTAMP + "&key="+API_KEY);
		getRequest2.addHeader("accept", "application/json");
		String response2 = executeRequest(getRequest2);
//		System.out.println(response2);

		Map<String, Object>  locMap = null;
		try {
			
			locMap = new ObjectMapper().readValue(response2, HashMap.class);
		
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String location = (String) locMap.get("timeZoneId");
		return location;
		
	}
		
	private static int getDistance(String origin, String target) {
		
//		 String origin = "Seattle";
			
			int distance = 0;

		    try {
		      
		      // specify the get request
		      HttpGet getRequest = new HttpGet("https://maps.googleapis.com/maps/api/distancematrix/json?" 
		      		+ "origins=" + origin + "&destinations=" + target + "&key=" + API_KEY);
				getRequest.addHeader("accept", "application/json");
				String result = executeRequest(getRequest);
//				System.out.println(result);
				
				Map<String, Object>  distMap = null;
				try {
					
					distMap = new ObjectMapper().readValue(result, HashMap.class);
				
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				Map<String, Object> distMap2 =  (Map)((List)( (Map)((List) distMap.get("rows")).get(0) ).get("elements")).get(0);
			    distance = (int) ((Map) distMap2.get("distance")).get("value");
				
		    }catch(Exception ex) {ex.printStackTrace();}
		    	
		    	return distance;
	}
		    
	private static String executeRequest(HttpRequestBase getRequest) {
		
			  HttpClient httpclient = new DefaultHttpClient();
			  StringBuffer sb =  new StringBuffer();

		    
			try {

		      System.out.println("executing request to ");

		      HttpResponse httpResponse = httpclient.execute(getRequest);
		      
		      if (httpResponse.getStatusLine().getStatusCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
					   + httpResponse.getStatusLine().getStatusCode());
				}
		      
		      HttpEntity entity = httpResponse.getEntity();

//		      System.out.println("----------------------------------------");
//		      System.out.println(httpResponse.getStatusLine());
//		      Header[] headers = httpResponse.getAllHeaders();
//		      for (int i = 0; i < headers.length; i++) {
//		        System.out.println(headers[i]);
//		      }
//		      System.out.println("----------------------------------------");

		      BufferedReader br = new BufferedReader(
                      new InputStreamReader((entity.getContent())));

				String output;
//				System.out.println("Output from Server .... \n");
				
				while ((output = br.readLine()) != null) {
					sb.append(output);
				}

		    } catch (Exception e) {
		      e.printStackTrace();
		    } finally {
		      // When HttpClient instance is no longer needed,
		      // shut down the connection manager to ensure
		      // immediate deallocation of all system resources
		      httpclient.getConnectionManager().shutdown();
		    }
			return sb.toString();
	}

}
