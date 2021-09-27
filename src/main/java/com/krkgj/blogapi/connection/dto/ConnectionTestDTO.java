package com.krkgj.blogapi.connection.dto;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data

@Entity(name="post_list")
public class ConnectionTestDTO 
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
	private Integer seq;

	private String title;

	private Integer category;

	private LocalDateTime createtime;

	private String tags;

	private String content;
	
	public ConnectionTestDTO() {}
	
	public ConnectionTestDTO(Integer seq, String title, Integer category, LocalDateTime createtime, String tags, String content)
	{
		this.seq = seq;
		this.title = title;
		this.category = category;
		this.createtime = createtime;
		this.tags = tags;
		this.content = content;
	}
	
//	@Column(name="seq")
//	private Integer seq;
//
//	@Column(name="title")
//	private String title;
//
//	@Column(name="category")
//	private Integer category;
//
//	@Column(name="createtime")
//	private LocalDateTime createtime;
//
//	@Column(name="tags")
//	private String tags;
//
//	@Column(name="content")
//	private String content;
	

}
