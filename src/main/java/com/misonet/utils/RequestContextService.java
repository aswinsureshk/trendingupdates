package com.misonet.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class RequestContextService {


    private final String COOKIE_NAME_USERID = "MISONET_USERID";
    
    private final String DOMAIN = "misonet.com";

    private final int COOKIE_TIMEOUT_HRS = 24;


    private final int COOKIE_TIMEOUT_DELETE = 0;


    public String getUserId(HttpServletRequest httpServletRequest) throws UnsupportedEncodingException {

        javax.servlet.http.Cookie[] cookies = httpServletRequest.getCookies();

        if (cookies != null && cookies.length > 0) {
            String userid = null;
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (COOKIE_NAME_USERID.equals(cookie.getName())) {
                    userid = URLDecoder.decode(cookie.getValue(), "UTF-8");

                    return userid;
                }
            }
        }

        return null;
    }

    public String validateCMUser(HttpServletRequest request) throws IOException {

        String userid = "";

        userid = getUserId(request);

        return userid;
    }

    public String addHours(int hrs) {
        DateFormat formatter = new SimpleDateFormat("E,dd-MMM-yyyy HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, hrs);
        String expiry = formatter.format(cal.getTime());

        return expiry;
    }
    
    
    public static String getDomainName(String url) throws MalformedURLException{
        if(!url.startsWith("http") && !url.startsWith("https")){
             url = "http://" + url;
        }        
        URL netUrl = new URL(url);
        String host = netUrl.getHost();
        if(host.startsWith("www")){
            host = host.substring("www".length()+1);
        }
        return host;
    }


    public void deleteCookie(HttpServletResponse response) {
    	
        String domainAndPath = "domain=" + DOMAIN + "; path=/;";

        domainAndPath = domainAndPath + "Expires=" + addHours(COOKIE_TIMEOUT_DELETE);

        response.addHeader("Set-Cookie", COOKIE_NAME_USERID + "=;" + domainAndPath + "; ");
    }

    public void createCookie(String uersid, HttpServletRequest request, HttpServletResponse response) throws IOException {


        String domainAndPath = "domain=" + request.getServerName() + "; path=/;";

        domainAndPath = domainAndPath + "Expires=" + addHours(COOKIE_TIMEOUT_HRS);

        response.addHeader("Set-Cookie", COOKIE_NAME_USERID + "=" + uersid + ";" + domainAndPath + "; ");

    }

    public String getUserName(String emailId) {

        String username = emailId.split("@")[0];

        return username;
    }

}

