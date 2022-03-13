package com.ems.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ems.dataobject.Task;

@Repository
public interface ITaskRepository extends CrudRepository<Task, Long> {

	List<Task> findByUserId(Long userId);
	
	@Query("SELECT t.status,count(t.status) from Task t WHERE t.userId=?1 group by t.status")
	List<Object[]> getCountByStatus(Long userId);
	@Query("SELECT coalesce(max(task.taskId), 0) FROM Task task")
	Long getMaxId();
	Optional<Task> findByTaskId(Long id);

}
