package com.project.greengrocery.controller;

import com.project.greengrocery.model.Category;
import com.project.greengrocery.service.GreengroceryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class GreengroceryControllerTest {
	@MockBean
	private GreengroceryService greengroceryService;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testIndex() throws Exception {
		// Given
		String fruitAccessToken = "fruitAccessTokenTest";
		String vegetableAccessToken = "vegetableAccessTokenTest";

		Cookie fruitCookie = new MockCookie("fruitAccessToken", fruitAccessToken);
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		// Then
		mockMvc.perform(get("/index").cookie(fruitCookie).cookie(vegetableCookie))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void testIndexFruitTokenIsNull() throws Exception {
		// Given
		String vegetableAccessToken = "vegetableAccessTokenTest";
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		//When
		String fruitAccessToken = "fruitAccessTokenTest1";
		Cookie fruitCookie = new Cookie("fruitAccessToken", fruitAccessToken);

		when(greengroceryService.getCookie(Category.FRUIT)).thenReturn(fruitCookie);

		// Then
		mockMvc.perform(get("/index").cookie(vegetableCookie))
				.andExpect(status().isOk())
				.andExpect(cookie().value("fruitAccessToken", fruitAccessToken))
				.andExpect(view().name("index"));
	}


	@Test
	public void testIndexVegetableTokenIsNull() throws Exception {
		// Given
		String fruitAccessToken = "fruitAccessTokenTest";
		Cookie fruitCookie = new MockCookie("fruitAccessToken", fruitAccessToken);

		//When
		String vegetableAccessToken = "vegetableAccessTokenTest1";
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		when(greengroceryService.getCookie(Category.VEGETABLE)).thenReturn(vegetableCookie);

		// Then
		mockMvc.perform(get("/index").cookie(fruitCookie))
				.andExpect(status().isOk())
				.andExpect(cookie().value("vegetableAccessToken", vegetableAccessToken))
				.andExpect(view().name("index"));
	}

}
