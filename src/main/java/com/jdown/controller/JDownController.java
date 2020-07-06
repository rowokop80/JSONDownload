package com.jdown.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jdown.exception.JsonDownloadException;
import com.jdown.model.Post;
import com.jdown.service.JDownService;

@CrossOrigin
@RestController
public class JDownController {

	@Autowired
	private JDownService jDownService;

	@GetMapping("/json/posts")
	@ResponseBody
	@ResponseStatus(code = HttpStatus.OK)
	public List<Post> jsonPosts() {

		try {
			return jDownService.getPosts();
		}catch (JsonDownloadException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), e);
		}
	}

	public void setjDownService(JDownService jDownService) {
		this.jDownService = jDownService;
	}
}
