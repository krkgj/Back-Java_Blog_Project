package com.krkgj.blogapi.post.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.krkgj.blogapi.post.dto.PostDTO;
import com.krkgj.blogapi.post.repository.PostRepository;
import com.krkgj.blogapi.utility.Utility;

import lombok.RequiredArgsConstructor;


// @RequiredArgsConstructor => 초기화가 되지 않은 fianl, @NonNull로 마크되어있는 모든 필드의 생성자를 생성.
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
	
	public List<PostDTO> getAllPostListOrderByAsc(String sortBy) 
	{
		List<PostDTO> list = new ArrayList<PostDTO>();
		
		postRepository.findAll(Utility.getSortOrderByAsc(sortBy)).forEach(dto -> list.add(dto));
		
		return list;
	}
	
	
	public List<PostDTO> getAllPostListOrderByDesc(String sortBy) 
	{
		List<PostDTO> list = new ArrayList<PostDTO>();
		
		postRepository.findAll(Utility.getSortOrderByDesc(sortBy)).forEach(dto -> list.add(dto));
		
		return list;
	}
	
	public PostDTO registPost(PostDTO post)
	{
		postRepository.save(post);
		
		return post;
	}

}
