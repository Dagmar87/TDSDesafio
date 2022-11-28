package com.dagmar87.projeto.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dagmar87.projeto.model.Url;
import com.dagmar87.projeto.service.UrlService;

@RestController
public class UrlController {
	
	public static final String HTTP_PREFIX = "http://";
	public static final String HTTPS_PREFIX = "https://";
	
	@Autowired
	private UrlService serv;
	
	@RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET)
	public ResponseEntity<Url> find(@PathVariable String shortUrl) throws URISyntaxException {
		
		Url obj = serv.findByShortUrl(shortUrl);
		if(obj == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		String redirectTo = obj.getOriginalUrl();
		if (!redirectTo.substring(0, HTTP_PREFIX.length()).equals(HTTP_PREFIX) &&
				!redirectTo.substring(0, HTTPS_PREFIX.length()).equals(HTTPS_PREFIX)) {
			redirectTo = HTTP_PREFIX.concat(redirectTo);
		}
		URI uri = new URI(redirectTo);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(uri);
		return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Url obj) {
		obj = serv.insert(obj);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{shortUrl}").buildAndExpand(obj.getShortUrl()).toUri();
		return ResponseEntity.created(uri).build();
	}

}
