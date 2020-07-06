package com.jdown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdown.exception.JsonDownloadException;
import com.jdown.model.Post;

@Service
public class JDownServiceImpl implements JDownService {

	@Autowired
	private DownloadService downloadService;

	@Autowired
	private MapperService<Post> mapperService;

	@Autowired
	private FileService<Post> fileService;

	public static String URL_STRING = "https://jsonplaceholder.typicode.com/posts";

	@Override
	public List<Post> getPosts() throws JsonDownloadException {

		String jsonString = downloadService.downloadJSON(URL_STRING);

		List<Post> postList = (List<Post>) mapperService.mapJsonArray(jsonString, Post.class);

		// parallel for faster save (2x faster)
		postList.parallelStream().filter(post -> post.getId()!=null).forEach(post -> fileService.saveAsFile(post, post.getId() + ".json"));

		return postList;
	}

	public void setDownloadService(DownloadService downloadService) {
		this.downloadService = downloadService;
	}

	public void setMapperService(MapperService<Post> mapperService) {
		this.mapperService = mapperService;
	}

	public void setFileService(FileService<Post> fileService) {
		this.fileService = fileService;
	}

}
