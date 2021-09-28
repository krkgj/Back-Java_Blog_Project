package com.krkgj.blogapi.post.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.krkgj.blogapi.post.dto.PostDTO;

@Repository
public interface PostRepository extends JpaRepository<PostDTO, Long>
{
	

}
