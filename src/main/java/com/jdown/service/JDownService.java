package com.jdown.service;

import java.util.List;

import com.jdown.exception.JsonDownloadException;
import com.jdown.model.Post;

public interface JDownService {

	public List<Post> getPosts() throws JsonDownloadException;
}
