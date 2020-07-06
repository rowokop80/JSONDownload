package com.jdown.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jdown.exception.JsonDownloadException;

@Service
public class MapperServiceImpl<T> implements MapperService<T> {

	@Autowired
	private ObjectMapper mapper;

	public List<T> mapJsonArray(String jsonString, Class<T> clazz) throws JsonDownloadException {
		TypeFactory t = TypeFactory.defaultInstance();
		try {
			return mapper.readValue(jsonString, t.constructCollectionType(ArrayList.class, clazz));
		}catch(IllegalArgumentException e) {
			throw new JsonDownloadException("Parse JSON String to Object List - Ilegal Argument for parsing String to Object List.");
		} catch (JsonMappingException e) {
			throw new JsonDownloadException("Parse JSON String to Object List - Mapping Problem.");
		} catch (JsonProcessingException e) {
			throw new JsonDownloadException("Parse JSON String to Object List - Mapping Problem.");
		}
	}
	
}
