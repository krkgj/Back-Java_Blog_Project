package com.krkgj.blogapi.post.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.post.dto.PostDTO;
import com.krkgj.blogapi.post.service.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController 
{
	@Resource(name="com.krkgj.blogapi.post.service.PostService")
	PostService postService;

	
	@GetMapping(value="/getpostlist", produces = {MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<PostDTO>> getAllPostList() 
	{
		List<PostDTO> list = postService.getAllPostList();

		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);
	}
	
	@PostMapping(value = "/registpost")
	public ResponseEntity<PostDTO> getAllPostList(@RequestBody PostDTO post) 
	{
		post.setCreatetime(LocalDateTime.now());
		
		PostDTO registedPost = postService.registPost(post);

		return new ResponseEntity<PostDTO>(registedPost, HttpStatus.OK);
	}
	
//	@PostMapping(value = "/registpost", produces = {MediaType.APPLICATION_JSON_VALUE })
//	public void getAllPostList(@RequestBody PostDTO post) 
//	{
//		System.out.println(post.getTitle());
//		PostDTO registedPost = postService.registPost(post);

//		return new ResponseEntity<PostDTO>(registedPost, HttpStatus.OK);
//	}
}
