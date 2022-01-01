package com.krkgj.blogapi.api.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krkgj.blogapi.api.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
	
}
