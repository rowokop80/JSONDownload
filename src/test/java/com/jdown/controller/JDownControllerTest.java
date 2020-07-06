package com.jdown.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import com.jdown.exception.JsonDownloadException;
import com.jdown.model.Post;
import com.jdown.service.JDownService;

@SpringBootTest
class JDownControllerTest {

	@Spy
	JDownController jDownControllerSpy;

	@Mock
	JDownService jDownServiceMock;

	public List<Post> preparePostList() {
		List<Post> postList = new ArrayList<Post>();
		Post p1 = new Post();
		p1.setId(1);
		p1.setTitle("title 1");
		Post p2 = new Post();
		p2.setId(2);
		p2.setTitle("title 2");
		Post p3 = new Post();
		p3.setId(3);
		p3.setTitle("title 3");
		Post p4 = new Post();
		p4.setId(4);
		p4.setTitle("title 4");

		postList.add(p1);
		postList.add(p2);
		postList.add(p3);
		postList.add(p4);

		return postList;
	}

	@Test
	void testGetPosts_all_ok() {

		jDownControllerSpy.setjDownService(jDownServiceMock);

		doReturn(preparePostList()).when(jDownServiceMock).getPosts();

		List<Post> retPostList = jDownControllerSpy.jsonPosts();

		verify(jDownServiceMock, times(1)).getPosts();

		assertNotNull(retPostList);
		assertEquals(4, retPostList.size());
	}

	@Test
	void testGetPosts_ThrowException() {

		jDownControllerSpy.setjDownService(jDownServiceMock);

		JsonDownloadException throwException =  
				new JsonDownloadException("test content error");
		
		doThrow(throwException).when(jDownServiceMock).getPosts();
		
		ResponseStatusException exception = assertThrows(ResponseStatusException.class,
				() -> jDownControllerSpy.jsonPosts());
		
		assertEquals("400 BAD_REQUEST \"test content error\"; nested exception is com.jdown.exception.JsonDownloadException: test content error",
				exception.getLocalizedMessage());

	}

}
