package com.jdown.service;

import com.jdown.exception.JsonDownloadException;

public interface DownloadService {
	
	/**
	 * download JSON from URL
	 * @param urlString - url 
	 * @return - JSON as string
	 * @throws JsonDownloadException
	 */
	public String downloadJSON(String urlString) throws JsonDownloadException;
}
