package com.krkgj.blogapi.api.post.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.krkgj.blogapi.api.post.dto.PostDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Data => 모든 필드의 게터, 세터들을 전부 생성한다.

// @Entity => JPA가 관리하게 해준다. / name : JPA에서 사용할 엔티티 일름을 지정,
@Entity

// 클래스에 존재하는 모든 필드에 대한 생성자를 자동으로 생성
@AllArgsConstructor

// 파라미터가 없는 생성자를 자동으로 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)

//@Build => 빌드패턴 적용!
@Builder(builderMethodName = "PostEntityBuilder")
// @Table => 엔티티와 매핑할 테이블을 지정, 생략 시 매핑한 엔티티 이름을 테이블 이름으로 사용한다.
@Table(name="post_list")
public class PostEntity 
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
	@Getter
	@Setter
	@Column(name="seq", nullable=true)
	private Long seq;

	// @Getter => 게터를 생성
	// @Setter => 세터를 생성
	// @Column => 객체 필드를 테이블 컬럼에 매핑시킨다.
	// name : 필드와 매핑할 테이블 컬럼 이름을 지정한다.
	// nullable : false는 not null 속성, true는 null 허용
	
	@Getter
	@Setter
	@Column(name="title", nullable=false)
	private String title;
	
	@Getter
	@Setter
	@Column(name="category", nullable=false)
	private Integer category;
	
	@Getter
	@Setter
	@Column(name="createtime", nullable=false)
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
	private LocalDateTime createtime;
	
	@Getter
	@Setter
	@Column(name="tags", nullable=false, insertable = false, updatable = false)
	private String tags;
	
	@Getter
	@Setter
	@Column(name="content", nullable=false)
	private String content;
	
	
	// Builder 패턴
	public static PostEntityBuilder builder(PostDTO postDTO)
	{
		return PostEntityBuilder()
				.seq(postDTO.getSeq())
				.title(postDTO.getTitle())
				.category(postDTO.getCategory())
				.createtime(postDTO.getCreatetime())
				.tags(postDTO.getTags())
				.content(postDTO.getContent());
	}
}
