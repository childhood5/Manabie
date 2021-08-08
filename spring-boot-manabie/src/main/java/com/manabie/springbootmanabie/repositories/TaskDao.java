package com.manabie.springbootmanabie.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manabie.springbootmanabie.entity.Tasks;



@Repository
public interface TaskDao extends CrudRepository<Tasks, Integer> {

	List<Tasks> findByUserIdAndCreatedDate(final String userId, final String createdDate);
}