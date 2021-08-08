package com.manabie.springbootmanabie.service;

import java.util.List;

import com.manabie.springbootmanabie.entity.Tasks;
import com.manabie.springbootmanabie.model.TaskDTO;

public interface TaskService {

	Tasks createTask(TaskDTO dto, String userId, String currentDate);
    
    List<Tasks> getTasks(final String userId, final String createdDate);


}