package com.jdown.service;

import java.util.List;

import com.jdown.exception.JsonDownloadException;

public interface MapperService<T> {


	/**
	 * transform JSONs array to list. <br> [{},{}...{}] 
	 * @param json - array JSONs string
	 * @param clazz - POJO class target
	 * @return transformed string to list of objects POJO instance of  class T
	 * @throws JsonDownloadException
	 */
	public List<T> mapJsonArray(String json, Class<T> clazz) throws JsonDownloadException;

}
