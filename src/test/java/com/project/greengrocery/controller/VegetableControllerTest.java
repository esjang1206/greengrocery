package com.project.greengrocery.controller;

import com.project.greengrocery.model.Category;
import com.project.greengrocery.service.GreengroceryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockCookie;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class VegetableControllerTest {
	@MockBean
	private GreengroceryService greengroceryService;

	@Autowired
	private WebApplicationContext ctx;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true))  // 필터 추가
				.build();
	}

	@Test
	public void testGetvegetableList() throws Exception {
		// Given
		String vegetableAccessToken = "vegetableAccessTokenTest";
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		List<String> expected = new ArrayList<>();
		expected.add("치커리");
		expected.add("깻잎");

		//When
		when(greengroceryService.getVegetableList(vegetableAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/vegetable/list").cookie(vegetableCookie))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("치커리"))
				.andExpect(jsonPath("$[1]").value("깻잎"));
	}

	@Test
	public void testGetVegetableListOfTokenIsNull() throws Exception {
		// Given
		List<String> expected = new ArrayList<>();
		expected.add("치커리");
		expected.add("깻잎");

		//When
		String vegetableAccessToken = "vegetableAccessTokenTest1";
		Cookie vegetableCookie = new Cookie("vegetableAccessToken", vegetableAccessToken);

		when(greengroceryService.getCookie(Category.VEGETABLE)).thenReturn(vegetableCookie);
		when(greengroceryService.getVegetableList(vegetableAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/vegetable/list"))
				.andExpect(status().isOk())
				.andExpect(cookie().value("vegetableAccessToken", vegetableAccessToken))
				.andExpect(jsonPath("$[0]").value("치커리"))
				.andExpect(jsonPath("$[1]").value("깻잎"));
	}

	@Test
	public void testGetVegetablePrice() throws Exception {
		// Given
		String name = "치커리";
		String expected = "1000";
		String vegetableAccessToken = "vegetableAccessTokenTest";
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		//When
		when(greengroceryService.getVegetablePrice(name, vegetableAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/vegetable/price").cookie(vegetableCookie).param("name", name))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data").value(expected));
	}

	@Test
	public void testGetVegetablePriceOfNameIsNull() throws Exception {
		// Given
		String name = "치커리";
		String expected = "1000";
		String vegetableAccessToken = "vegetableAccessTokenTest";
		Cookie vegetableCookie = new MockCookie("vegetableAccessToken", vegetableAccessToken);

		//When
		when(greengroceryService.getVegetablePrice(name, vegetableAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/vegetable/price").cookie(vegetableCookie))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.errorCode").value(1))
				.andExpect(jsonPath("$.data").value("NAME_NULL"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetVegetablePriceOfTokenIsNull() throws Exception {
		// Given
		String name = "치커리";
		String expected = "1000";

		//When
		String vegetableAccessToken = "vegetableAccessTokenTest1";
		Cookie vegetableCookie = new Cookie("vegetableAccessToken", vegetableAccessToken);

		when(greengroceryService.getCookie(Category.VEGETABLE)).thenReturn(vegetableCookie);
		when(greengroceryService.getVegetablePrice(name, vegetableAccessToken)).thenReturn(expected);

		// Then
		mockMvc.perform(get("/vegetable/price").param("name", name))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data").value(expected))
				.andExpect(cookie().value("vegetableAccessToken", vegetableAccessToken))
				.andExpect(status().isOk());
	}

}
