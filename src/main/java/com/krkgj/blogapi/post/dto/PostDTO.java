package com.krkgj.blogapi.post.dto;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="post_list")
public class PostDTO 
{
	
	//	IDENTITY : 데이터베이스에 위임(MYSQL)
	//		-> Auto_Increment
	//	SEQUENCE : 데이터베이스 시퀀스 오브젝트 사용(ORACLE)
	//		-> @SequenceGenerator 필요
	//	TABLE : 키 생성용 테이블 사용, 모든 DB에서 사용
	//		-> @TableGenerator 필요
	//	AUTO : 방언에 따라 자동 지정, 기본값

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Getter
	@Setter
	@Column(name="title")
	private String title;
	
	@Getter
	@Setter
	@Column(name="category")
	private Integer category;
	
	@Getter
	@Setter
	@Column(name="createtime")
	private LocalDateTime createtime;
	
	@Getter
	@Setter
	@Column(name="tags")
	private String tags;
	
	@Getter
	@Setter
	@Column(name="content")
	private String content;
}
