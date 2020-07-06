package com.jdown.service;

import com.jdown.exception.JsonDownloadException;

public interface FileService<T> {

	/**
	 * save file on HD. Folder path is taken from variable folderPath
	 * @param content - any POJO java object 
	 * @param fileName
	 * @throws JsonDownloadException
	 */
	void saveAsFile(T content, String fileName) throws JsonDownloadException;

}
