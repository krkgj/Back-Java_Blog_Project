package com.krkgj.blogapi.api.post.dto;

import java.time.LocalDateTime;

import com.krkgj.blogapi.api.post.entity.PostEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "PostDTOBuilder")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDTO 
{
	private Long 			seq;
	private String 			title;	
	private Integer 		category;	
	private LocalDateTime 	createtime;	
	private String 			tags;	
	private String 			content;
	
	public static PostDTOBuilder builder(PostEntity postEntity) 
	{
		return PostDTOBuilder()
				.seq(postEntity.getSeq())
				.title(postEntity.getTitle())
				.category(postEntity.getCategory())
				.createtime(postEntity.getCreatetime())
				.tags(postEntity.getTags())
				.content(postEntity.getContent());

	}
}
