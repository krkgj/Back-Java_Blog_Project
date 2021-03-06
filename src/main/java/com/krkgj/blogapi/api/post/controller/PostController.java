package com.krkgj.blogapi.api.post.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krkgj.blogapi.api.post.dto.PostDTO;
import com.krkgj.blogapi.api.post.service.PostService;
import com.krkgj.blogapi.framework.utility.Utility;

@RestController
@RequestMapping("/post")
public class PostController 
{
	
	private static final Logger logger = LoggerFactory.getLogger(PostController.class);

	
	@Resource(name="com.krkgj.blogapi.api.post.service.PostService")
	PostService postService;

	// @RequestParam은 URL의 ? 뒤에 전송되는 키를 value에 받는다. required=false로 필수값 설정을 해제(DEFAULT는 true)
	@GetMapping(value="/list", produces = {MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<PostDTO>> getAllPostList( @RequestParam(value = "sort-direction", required=false) String sortingDirection,
															@RequestParam(value = "sort-by", required=false) String sortBy) 
	{
		logger.info("Request \"/get-post-list\".........");
		logger.info("sortingDirection => " + sortingDirection);
		logger.info("sortBy => " + sortBy);
		
		List<PostDTO> list = null;
		
		if(Utility.isNull(sortingDirection) && Utility.isNull(sortBy))
		{
			logger.info("No value sortingValue - set Default! ");

			list = postService.getAllPostList();
		}
		else
		{
			if(sortingDirection.equals("asc"))
			{
				list = postService.getAllPostListOrderByAsc(sortBy);
			}
			else if(sortingDirection.equals("desc"))
			{
				list = postService.getAllPostListOrderByDesc(sortBy);
				for (PostDTO postDTO : list) {
					System.out.println("controller > " +postDTO.getCreatetime());
				}
			}
		}
		return new ResponseEntity<List<PostDTO>>(list, HttpStatus.OK);
	}
	
	
	// 파라메터에 @RequestBody로 데이터를 인자인 post에 할당한다.
	@PostMapping(value = "/post" ,produces = {MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<PostDTO> getAllPostList(@RequestBody PostDTO postDto, HttpServletRequest request) 
	{
		postDto.setCreatetime(LocalDateTime.now());
		System.out.println(postDto);
		System.out.println(request);
		
		PostDTO registedPost = postService.registPost(postDto);
		
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
