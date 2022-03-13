package com.ems.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource("classpath:application.properties")
public class EMSConfiguration {
	
	@Value("${rest.baseUrl}")
	private String BASE_URL;
	/*
	 * @Bean public HttpRestClient<User> initialize() { return new
	 * HttpRestClient<User>(User.class); }
	 */
	
	/*
	 * @LoadBalanced
	 * 
	 * @Bean public WebClient initializeWebClient() { return
	 * WebClient.create(baseUrl); }
	 */

	public String getBaseUrl()
	{
		return BASE_URL;
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate initialize() {
		return new RestTemplate(); 
		}

}
