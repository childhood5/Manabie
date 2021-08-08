package com.manabie.springbootmanabie.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manabie.springbootmanabie.entity.Users;



@Repository
public interface UserDao extends CrudRepository<Users, Integer> {

	Users findById(String id);
    
}