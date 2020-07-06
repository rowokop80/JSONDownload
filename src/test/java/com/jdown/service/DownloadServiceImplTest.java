package com.jdown.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import com.jdown.exception.JsonDownloadException;

@SpringBootTest
public class DownloadServiceImplTest {

	@Spy
	DownloadServiceImpl mapperServiceimplSpy;

	@Mock
	HttpURLConnection connectionMock;

	String urlString = "https://some-url";

	@Test
	public void downloadJSON_correct_test() {

		String initialString = "this is text from request by url.";
		// my mock for input stream with concrete value for compare result
		InputStream inputStreamMock = new ByteArrayInputStream(initialString.getBytes());

		doReturn(connectionMock).when(mapperServiceimplSpy).createConnection(urlString);
		try {
			doReturn(inputStreamMock).when(connectionMock).getInputStream();
		} catch (IOException e) {
			// do nothing
		}

		String answer = mapperServiceimplSpy.downloadJSON(urlString);
		assertNotNull(answer);
		assertEquals("this is text from request by url.", answer);
	}

	@Test
	public void downloadJSON_incorrect_create_connection_test() {

		doThrow(JsonDownloadException.class).when(mapperServiceimplSpy).createConnection(urlString);

		JsonDownloadException exception = assertThrows(JsonDownloadException.class,
				() -> mapperServiceimplSpy.createConnection(urlString));
		
		assertEquals(null, exception.getMessage());

	}
	
	@Test
	public void downloadJSON_incorrect_inputStream_test() {

		InputStream inputStreamMock = null;

		doReturn(connectionMock).when(mapperServiceimplSpy).createConnection(urlString);
		try {
			doReturn(inputStreamMock).when(connectionMock).getInputStream();
		} catch (IOException e) {
			// do nothing
		}
		
		JsonDownloadException exception = assertThrows(JsonDownloadException.class,
				() -> mapperServiceimplSpy.downloadJSON(urlString));
		
		assertEquals("Problem with download from URL", exception.getMessage());

	}
}
