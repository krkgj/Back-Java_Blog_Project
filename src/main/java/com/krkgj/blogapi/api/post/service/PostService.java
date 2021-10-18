package com.krkgj.blogapi.api.post.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.krkgj.blogapi.api.post.dto.PostDTO;
import com.krkgj.blogapi.api.post.entity.PostEntity;
import com.krkgj.blogapi.api.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;


// @RequiredArgsConstructor => 초기화가 되지 않은 final, @NonNull로 마크되어있는 모든 필드의 생성자를 생성.
@Service("com.krkgj.blogapi.api.post.service.PostService")
@RequiredArgsConstructor
public class PostService 
{
	private final PostRepository postRepository;
	
	public Sort getSortOrderByAsc(String str)	{ return Sort.by(Sort.Direction.ASC, str); }
	public Sort getSortOrderByDesc(String str)	{ return Sort.by(Sort.Direction.DESC, str); }
	
	public List<PostDTO> getAllPostList()
	{
		List<PostDTO> list = new ArrayList<>();
		postRepository.findAll().forEach(entity -> {
			PostDTO postDto = PostDTO.builder(entity).build();
			list.add(postDto);
		});
		
		return list;
	}
	
	public List<PostDTO> getAllPostListOrderByAsc(String sortBy) 
	{
		List<PostDTO> list = new ArrayList<PostDTO>();
		
		postRepository.findAll(getSortOrderByAsc(sortBy)).forEach(entity -> {
			PostDTO postDto = PostDTO.builder(entity).build();
			list.add(postDto);
		});
		
		return list;
	}
	
	
	public List<PostDTO> getAllPostListOrderByDesc(String sortBy) 
	{
		List<PostDTO> list = new ArrayList<PostDTO>();
		
		postRepository.findAll(getSortOrderByDesc(sortBy)).forEach(entity -> {
			PostDTO postDto = PostDTO.builder(entity).build();
			System.out.println(postDto.getCreatetime());
			list.add(postDto);
		});
		
		return list;
	}
	
	public PostDTO registPost(PostDTO post)
	{
		PostEntity postEntity = PostEntity.builder(post).build();

		System.out.println("svc regist > " + postEntity.getTitle());
		System.out.println("svc regist > " + postEntity.getTags());
		System.out.println("svc regist > " + postEntity.getContent());
		System.out.println("svc regist > " + postEntity.getCategory());
		
		postRepository.save(postEntity);
		
		return post;
	}


}
