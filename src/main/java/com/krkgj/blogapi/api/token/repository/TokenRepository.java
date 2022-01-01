package com.krkgj.blogapi.api.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krkgj.blogapi.api.token.entity.TokenEntity;


@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long>
{

}