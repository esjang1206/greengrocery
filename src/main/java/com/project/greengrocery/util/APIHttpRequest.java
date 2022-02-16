package com.project.greengrocery.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
public class APIHttpRequest {
	public static final String FRUIT_URL = "http://fruit.api.postype.net";
	public static final String VEGETABLE_URL = "http://vegetable.api.postype.net";

	private static final int CONNECTION_LIMIT_TIME = 3000; // 연결 시간

	/**
	 * @param url
	 * @return ResponseEntity
	 */
	public static ResponseEntity requestGetForJson(String url) {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(CONNECTION_LIMIT_TIME);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity entity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			log.info("CONNECTION FAIL ! : ", responseEntity.getBody());
			return null;
		}
		return responseEntity;
	}

	/**
	 * @param url
	 * @param accessToken
	 * @return string
	 */
	public static String requestGetForJson(String url, String accessToken) {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(CONNECTION_LIMIT_TIME);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", accessToken);
		HttpEntity entity = new HttpEntity<>(headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			log.info("CONNECTION FAIL ! : ", responseEntity.getBody());
			return null;
		}
		return responseEntity.getBody();
	}

	/**
	 * @param url
	 * @param accessToken
	 * @return List
	 */
	public static List requestGetForList(String url, String accessToken) {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(CONNECTION_LIMIT_TIME);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", accessToken);
		HttpEntity entity = new HttpEntity<>(headers);

		ResponseEntity<List<String>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<String>>() {
				});
		if (responseEntity.getStatusCode() != HttpStatus.OK) {
			log.info("CONNECTION FAIL ! : ", responseEntity.getBody());
			return null;
		}
		List<String> list = responseEntity.getBody();
		return list;
	}
}
