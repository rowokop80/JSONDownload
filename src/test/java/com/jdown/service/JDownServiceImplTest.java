package com.jdown.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdown.model.Post;

@SpringBootTest
class JDownServiceImplTest {

	@Spy
	JDownServiceImpl jDownServiceImplSpy;
	
	@Mock
	DownloadService downloadServiceMock;

	@Mock
	private MapperService<Post> mapperServiceMock;

	@Mock
	private FileService<Post> fileServiceMock;
	
	@Mock
	Post post;
	
	
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
		
		List<Post> postList = preparePostList();
		
		jDownServiceImplSpy.setDownloadService(downloadServiceMock);
		jDownServiceImplSpy.setMapperService(mapperServiceMock);
		jDownServiceImplSpy.setFileService(fileServiceMock);
		
		doReturn(postList).when(mapperServiceMock).mapJsonArray(any(), any());
		
		List<Post> retPostList = jDownServiceImplSpy.getPosts();
		
		verify(downloadServiceMock, times(1)).downloadJSON(any()); 
		verify(mapperServiceMock, times(1)).mapJsonArray(any(), any()); 
		verify(fileServiceMock, times(4)).saveAsFile(any(), any());

		assertNotNull(retPostList);
		assertEquals(4, retPostList.size());
	}
	
	@Test
	void testGetPosts_missingID() {
		List<Post> postList = preparePostList();
		
		postList.get(0).setId(null);
		postList.get(1).setId(null);
		
		jDownServiceImplSpy.setDownloadService(downloadServiceMock);
		jDownServiceImplSpy.setMapperService(mapperServiceMock);
		jDownServiceImplSpy.setFileService(fileServiceMock);
		
		doReturn(postList).when(mapperServiceMock).mapJsonArray(any(), any());
		
		List<Post> retPostList = jDownServiceImplSpy.getPosts();
		
		verify(downloadServiceMock, times(1)).downloadJSON(any()); 
		verify(mapperServiceMock, times(1)).mapJsonArray(any(), any()); 
		verify(fileServiceMock, times(2)).saveAsFile(any(), any());   /// list contain 4 elements but only 2 has id. So 2 files should be saved

		assertNotNull(retPostList);
		assertEquals(4, retPostList.size());
	}
}
