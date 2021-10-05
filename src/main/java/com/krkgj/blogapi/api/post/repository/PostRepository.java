package com.krkgj.blogapi.api.post.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krkgj.blogapi.api.post.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>
{
	

}
