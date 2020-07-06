package com.jdown.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdown.exception.JsonDownloadException;

@Service
public class FileServiceImpl<T> implements FileService<T> {

	@Value("${folderPath}")
	private String folderPath;
	
	private Gson gson; 
	
    @PostConstruct
    public void init() {
    	gson = new GsonBuilder().setPrettyPrinting().create();
    }
	
	
	public void saveAsFile(T content, String fileName)  throws JsonDownloadException {
		try {
			Writer writerJson = new FileWriter(folderPath+fileName);
			gson.toJson(content, writerJson);
			writerJson.close();
		}catch(IOException e) {
			throw new JsonDownloadException("Impossible save file. Check folder patch and file name.");
		}
	}
	
}
