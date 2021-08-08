package com.manabie.springbootmanabie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manabie.springbootmanabie.entity.Tasks;
import com.manabie.springbootmanabie.model.TaskDTO;
import com.manabie.springbootmanabie.repositories.TaskDao;

@Service
public class TaskServiceImpl implements TaskService {
	
	private TaskDao taskDao;
	
	@Autowired
	public TaskServiceImpl(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	@Override
	public Tasks createTask(TaskDTO dto, String userId, String currentDate) {
		Tasks task = new Tasks();
		task.setContent(dto.getContent());
		task.setUserId(userId);
		task.setCreatedDate(currentDate);
		return taskDao.save(task);
	}

	@Override
	public List<Tasks> getTasks(final String userId, final String createdDate) {
		return taskDao.findByUserIdAndCreatedDate(userId, createdDate);
	}

	

}
