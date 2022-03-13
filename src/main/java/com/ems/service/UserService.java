package com.ems.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.ems.dataobject.User;
import com.ems.service.client.HttpRestClient;
import com.ems.service.response.RestResponse;

@Service
public class UserService implements IEntityService<User> {

	@Autowired
	HttpRestClient<User> restClient;
	
	@Override
	public Iterable<User> getAllEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User saveEntity(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getUserByName(String name) {
		List<User> users = new ArrayList<User>();
		try
		{
			ParameterizedTypeReference<RestResponse<List<User>>> typeReference = new ParameterizedTypeReference<RestResponse<List<User>>>() {};
			users =  restClient.getFromRest(name,typeReference);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return users.size()>0?users.get(0):new User();
	}

	@Override
	public User getEntity(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
