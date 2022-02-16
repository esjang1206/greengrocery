package com.project.greengrocery.service;

import com.project.greengrocery.model.Category;
import com.project.greengrocery.util.APIHttpRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Cookie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GreengroceryServiceTest {
	@InjectMocks
	private GreengroceryService greengroceryService;

	@Test
	public void testGetFruitAccessToken() {
		//Given
		String accessToken = "{\"accessToken\":\"testAccessToken\"}";

		//When
		ResponseEntity responseEntity = new ResponseEntity(accessToken, HttpStatus.OK);
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForJson(anyString())).thenReturn(responseEntity);

		//Then
		Cookie cookie = greengroceryService.getCookie(Category.FRUIT);
		assertEquals(cookie.getValue(), "testAccessToken");
	}

	@Test
	public void testGetVegetableAccessToken() {

		//Given
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Set-Cookie", "Authorization=testAccessToken!; Path=/");

		//When
		ResponseEntity responseEntity = new ResponseEntity(headers, HttpStatus.OK);
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForJson(anyString())).thenReturn(responseEntity);

		//Then
		Cookie cookie = greengroceryService.getCookie(Category.VEGETABLE);
		assertEquals(cookie.getValue(), "testAccessToken!");
	}

	@Test
	public void testGetFruitList() {
		String accessToken = "accessTokenTest";

		//Given
		List expected = new ArrayList();
		expected.add("사과");
		expected.add("바나나");

		//When
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForList(anyString(), anyString())).thenReturn(expected);

		//Then
		List actual = greengroceryService.getFruitList(accessToken);
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

	@Test
	public void testGetVegetableList() {
		String accessToken = "accessTokenTest";

		//Given
		List expected = new ArrayList();
		expected.add("치커리");
		expected.add("깻잎");

		//When
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForList(anyString(), anyString())).thenReturn(expected);

		//Then
		List actual = greengroceryService.getVegetableList(accessToken);
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

	@Test
	public void testGetFruitPrice() {
		String accessToken = "accessTokenTest";

		//Given
		String name = "바나나";
		String expected = "2000";

		//When
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForJson(anyString(), anyString())).thenReturn(expected);

		//Then
		String actual = greengroceryService.getFruitPrice(name, accessToken);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetVegetablePrice() {
		String accessToken = "accessTokenTest";

		//Given
		String name = "치커리";
		String expected = "11000";

		//When
		mockStatic(APIHttpRequest.class);
		when(APIHttpRequest.requestGetForJson(anyString(), anyString())).thenReturn(expected);

		//Then
		String actual = greengroceryService.getVegetablePrice(name, accessToken);
		assertEquals(expected, actual);
	}
}
