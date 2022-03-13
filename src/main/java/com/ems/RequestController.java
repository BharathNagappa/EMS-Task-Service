package com.ems;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ems.dataobject.Task;
import com.ems.service.TaskService;
import com.ems.service.response.RestResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
public class RequestController {
	
	@Autowired
	TaskService taskService;

	
	@RequestMapping(method = RequestMethod.GET,path = "/tasks/{userId}")
	public RestResponse<List<Task>> getUserTasks(@PathVariable(name ="userId")  Long userId) throws JsonMappingException, JsonProcessingException
	{
		RestResponse<List<Task>> restResponse = new RestResponse<List<Task>>();
		try
		{
			List<Task> tasks = taskService.getTasksByUser(userId);
			restResponse.setData(tasks);
			restResponse.setResponseMessage("Success");
			restResponse.setStatusCode(HttpStatus.OK);
		}
		catch (Exception e) {

			restResponse.setResponseMessage(e.getMessage());
			restResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return restResponse;
	}
	
	@RequestMapping(method = RequestMethod.GET,path = "/countByStatus/{userId}")
	public RestResponse<List<Task>> countByStatus(@PathVariable(name ="userId")  Long userId)
	{
		RestResponse<List<Task>> restResponse = new RestResponse<List<Task>>();
		try
		{
			Task task = taskService.getCountByStatus(userId);
			restResponse.setData(Arrays.asList(task));
			restResponse.setResponseMessage("Success");
			restResponse.setStatusCode(HttpStatus.OK);
		}
		catch (Exception e) {

			restResponse.setResponseMessage(e.getMessage());
			restResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return restResponse;
	}
	
	@PostMapping(path = "/task")
	public RestResponse<Task> createTask(@RequestBody Task task)
	{
		RestResponse<Task> restResponse = new RestResponse<Task>();
		try
		{
			task.setCreateDate(new Date());
			task = taskService.saveEntity(task);
			restResponse.setData(task);
			restResponse.setResponseMessage("Success");
			restResponse.setStatusCode(HttpStatus.OK);
		}
		catch (Exception e) {

			restResponse.setResponseMessage(e.getMessage());
			restResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return restResponse;
	}

	@GetMapping(path ="/task/{id}")
	public RestResponse<List<Task>> getTask(@PathVariable(name="id") Long taskId)
	{
		RestResponse<List<Task>> restResponse = new RestResponse<List<Task>>();
		try
		{
			Task task = taskService.getEntity(taskId);
			restResponse.setData(Arrays.asList(task));
			restResponse.setResponseMessage("Success");
			restResponse.setStatusCode(HttpStatus.OK);
		}
		catch (Exception e) {

			restResponse.setResponseMessage(e.getMessage());
			restResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return restResponse;
		
	}
}
