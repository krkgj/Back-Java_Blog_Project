package com.krkgj.blogapi.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.krkgj.blogapi.post.dto.PostDTO;
import com.krkgj.blogapi.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service("com.krkgj.blogapi.post.service.PostService")
@RequiredArgsConstructor
public class PostService 
{
	private final PostRepository postRepository;
	
	public List<PostDTO> getAllPostList()
	{
		List<PostDTO> list = new ArrayList<>();
		postRepository.findAll().forEach(dto -> list.add(dto));
		
		return list;
	}
	
	public PostDTO registPost(PostDTO post)
	{
		postRepository.save(post);
		
		return post;
	}

}
