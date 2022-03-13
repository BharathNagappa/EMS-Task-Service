package com.ems.service.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ems.config.EMSConfiguration;
import com.ems.dataobject.User;
import com.ems.service.response.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Component
public class HttpRestClient<T> {
	
	@Autowired
	 RestTemplate restTemplate; 
	
	@Autowired
	EMSConfiguration config;
	
	@Autowired
	private HttpServletRequest request;
	
	String baseUrl;
	public HttpRestClient() {
		 
	}
	
	
	public List<T> getFromRest(String userName,ParameterizedTypeReference<RestResponse<List<T>>> typeReference) throws JsonMappingException, JsonProcessingException, URISyntaxException
	{
		
		List<T> data = null;
		try
		{
			ResponseEntity<RestResponse<List<T>>> response;
			RequestEntity<Void> requestEntity = getRequestEntity(userName);

			//response = restTemplate.getForObject(baseUrl+userName, RestResponse.class);
			response = restTemplate.exchange(requestEntity, typeReference);
			if(response.hasBody())
			{
				data = response.getBody().getData();
				System.out.println(data);
				return (List<T>) data;
			}
			
		}
		catch(Exception e)
		{
			data = new ArrayList<T>();
		}
		
		return data;
	}


	private RequestEntity<Void> getRequestEntity(String userName) throws URISyntaxException {
		baseUrl = config.getBaseUrl();
		String accessToken = getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+accessToken);
		RequestEntity<Void> requestEntity = RequestEntity
			     .get(new URI(baseUrl+"getuser/"+userName))
			     .headers(headers)
			     .build();
		return requestEntity;
	}


	public String getAccessToken() {
		baseUrl = config.getBaseUrl();
		User user;
		if(SecurityContextHolder.getContext().getAuthentication()!=null)
		{
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		else
		{
			String un = request.getHeader("username");
			String pwd = request.getHeader("password");
			user = new User();
			user.setUserId(un);
			user.setPassword(pwd);
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userId", user.getUserId());
		map.put("password", user.getPassword());
		RestResponse<String> restResponse = restTemplate.postForObject(baseUrl+"/authenticate",map, RestResponse.class);
		return restResponse.getData();
		
	}
	

	/*
	 * public static void main(String[] args) throws JsonMappingException,
	 * JsonProcessingException {
	 * ParameterizedTypeReference<RestResponse<List<UserDO>>> typeReference = new
	 * ParameterizedTypeReference<RestResponse<List<UserDO>>>() {};
	 * HttpRestClient<UserDO> httpClient = new HttpRestClient<UserDO>();
	 * httpClient.webClient = WebClient.create("http://localhost:8081/");
	 * List<UserDO> userDetails = (List<UserDO>)
	 * httpClient.getFromRest("Bharath",typeReference);
	 * 
	 * 
	 * System.out.println(userDetails); for (UserDO userDO : userDetails) {
	 * System.out.println(new ObjectMapper().writeValueAsString(userDO));
	 * 
	 * } }
	 */

}
