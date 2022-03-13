package com.ems.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.dataobject.Task;
import com.ems.repo.ITaskRepository;

@Service
public class TaskService implements IEntityService<Task> {

	@Autowired
	ITaskRepository repo;
	
	@Override
	public Iterable<Task> getAllEntities() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Task saveEntity(Task entity) {
		// TODO Auto-generated method stub
		Long id = repo.getMaxId();
		entity.setId(id+1);
		return repo.save(entity);
	}

	public List<Task> getTasksByUser(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}
	
	public Task getCountByStatus(Long userId)
	{
		Map<String, String> map =  new HashMap<String,String>();
		List<Object[]> a = repo.getCountByStatus(userId); 
		Task task = null;
		long total = 0;
		for (Object[] objects : a) {
			Long value = (Long)objects[1];
			map.put((String)objects[0], String.valueOf(value));
			total = total+value;
		}
		//map.put("total", String.valueOf(total));
		if(map.size()>0)
		{
			task = new Task();
			task.setAdditionalProperties(map);
		}
		return task;
	}

	@Override
	public Task getEntity(Long id) {
		return repo.findByTaskId(id).orElse(new Task());
	}
}
