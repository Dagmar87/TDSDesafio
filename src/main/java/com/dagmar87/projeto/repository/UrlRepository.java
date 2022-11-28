package com.dagmar87.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dagmar87.projeto.model.Url;

public interface UrlRepository extends JpaRepository<Url, Integer>{
	
	Optional<Url> findByShortUrl(String shortUrl);

}
