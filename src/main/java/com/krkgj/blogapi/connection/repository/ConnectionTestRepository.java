package com.krkgj.blogapi.connection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krkgj.blogapi.connection.dto.ConnectionTestDTO;

@Repository
public interface ConnectionTestRepository extends JpaRepository<ConnectionTestDTO, Long>
{
	public List<ConnectionTestDTO> findBySeq(Long seq);
}