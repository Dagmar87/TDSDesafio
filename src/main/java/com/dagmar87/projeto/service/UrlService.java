package com.dagmar87.projeto.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dagmar87.projeto.model.Url;
import com.dagmar87.projeto.others.IdConverter;
import com.dagmar87.projeto.repository.UrlRepository;

@Service
public class UrlService {
	
	@Autowired
	private UrlRepository rep;
	
	private IdConverter conv = IdConverter.getInstance();
	
	public Url findByShortUrl(String shortUrl) {
		Optional<Url> obj = rep.findByShortUrl(shortUrl);
		return obj.orElse(null);
	}
	
	public Url insert(Url obj) {
		obj.setId(null);
		obj = rep.save(obj);
		obj.setShortUrl(conv.toBase62(String.valueOf(obj.getId())));
		return rep.save(obj);
	}

}
