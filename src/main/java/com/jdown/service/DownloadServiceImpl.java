package com.jdown.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

import org.springframework.stereotype.Service;

import com.jdown.exception.JsonDownloadException;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Override
	public String downloadJSON(String urlString) throws JsonDownloadException {

		try {
			HttpURLConnection connection = createConnection(urlString);
			return useConnect(connection);
		} catch (JsonDownloadException e) {
			throw e;
		}
	}

	protected HttpURLConnection createConnection(String urlString) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "application/json; utf-8");
			connection.setRequestProperty("Accept", "application/json");
			return connection;
		} catch (IOException e) {
			throw new JsonDownloadException("Problem with create connection.");
		}
	}

	protected String useConnect(HttpURLConnection connection) {
		try {
			StringBuilder response = new StringBuilder();
			try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
			}
			return response.toString();
		} catch (IOException | NullPointerException e) {
			throw new JsonDownloadException("Problem with download from URL");
		}
	}
}
