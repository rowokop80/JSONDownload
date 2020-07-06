package com.jdown.exception;


public class JsonDownloadException extends RuntimeException {

	private static final long serialVersionUID = 8306048235176720886L;
	
	public JsonDownloadException(String errorMessage){
		super(errorMessage);
	}

	
}
