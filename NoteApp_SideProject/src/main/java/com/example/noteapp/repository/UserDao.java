package com.example.noteapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.noteapp.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {

	@Query(value="SELECT * FROM users u WHERE u.username = :username", nativeQuery=true)
	UserEntity findByUsername(@Param("username")String username);
	
}
