package com.jdown.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdown.exception.JsonDownloadException;
import com.jdown.model.Post;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;



@SpringBootTest
public class MapperServiceImplTest {

	@Autowired
	MapperService<Post> mapperService;
	
	String jsonStringNull = null;
	String jsonStringBlank = "";
	String jsonStringEmpty = "{}";
	String jsonStringEmptyList = "[{}]";
	
	String jsonString1 = "{\"userId\": 11,\"id\": 1,\"title\": \"title 1\",\"body\": \"body 1\"}";
	
	String jsonStringAllNull = "{\"userId\": null,\"id\": null,\"title\": null,\"body\": null}";
	
	String jsonStringList4 = 
			 "[{\"userId\": 11,\"id\": 1,\"title\": \"title 1\",\"body\": \"body 1\"},"
			+ "{\"userId\": 22,\"id\": 2,\"title\": \"title 2\",\"body\": \"body 2\"},"
			+ "{\"userId\": 33,\"id\": 3,\"title\": \"title 3\",\"body\": \"body 3\"},"
			+ "{\"userId\": 44,\"id\": 4,\"title\": \"title 4\",\"body\": \"body 4\"}]";


	@Test
	void mapJsonArray_NotExist_null_test() {
		JsonDownloadException exception = assertThrows(
				JsonDownloadException.class,
			        () -> mapperService.mapJsonArray(jsonStringNull, Post.class)
			    );
			    assertEquals("Parse JSON String to Object List - Ilegal Argument for parsing String to Object List.", exception.getMessage());		
	}
	
	@Test
	void mapJsonArray_NotExist_blank_test() {
		JsonDownloadException exception = assertThrows(
				JsonDownloadException.class,
			        () -> mapperService.mapJsonArray(jsonStringBlank, Post.class)
			    );
			    assertEquals("Parse JSON String to Object List - Mapping Problem.", exception.getMessage());		
	}
	
	@Test
	void mapJsonArray_NotExist_empty_test() {
		List<Post> postList = mapperService.mapJsonArray(jsonStringEmptyList, Post.class);
		
		assertNotNull(postList);	
		assertEquals(1, postList.size());
		assertEquals(null, postList.get(0).getId());
		assertEquals(null, postList.get(0).getUserId());
		assertEquals(null, postList.get(0).getTitle());
		assertEquals(null, postList.get(0).getBody());
	}
	
	@Test
	void mapJsonArray_Multi_test() {
		List<Post> postList = mapperService.mapJsonArray(jsonStringList4, Post.class);
		
		assertNotNull(postList);	
		assertEquals(4, postList.size());

		assertEquals(1, postList.get(0).getId());
		assertEquals(11, postList.get(0).getUserId());
		assertEquals("title 1", postList.get(0).getTitle());
		assertEquals("body 1", postList.get(0).getBody());
		
		assertEquals(2, postList.get(1).getId());
		assertEquals(22, postList.get(1).getUserId());
		assertEquals("title 2", postList.get(1).getTitle());
		assertEquals("body 2", postList.get(1).getBody());
		
		assertEquals(3, postList.get(2).getId());
		assertEquals(33, postList.get(2).getUserId());
		assertEquals("title 3", postList.get(2).getTitle());
		assertEquals("body 3", postList.get(2).getBody());
		
		assertEquals(4, postList.get(3).getId());
		assertEquals(44, postList.get(3).getUserId());
		assertEquals("title 4", postList.get(3).getTitle());
		assertEquals("body 4", postList.get(3).getBody());
		
	
	}
}
