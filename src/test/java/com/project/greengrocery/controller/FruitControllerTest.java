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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class FruitControllerTest {
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
	public void testGetFruitList() throws Exception {
		// Given
		String fruitAccessToken = "fruitAccessTokenTest";
		Cookie fruitCookie = new MockCookie("fruitAccessToken", fruitAccessToken);

		List<String> expected = new ArrayList<>();
		expected.add("사과");
		expected.add("배");

		//When
		when(greengroceryService.getFruitList(fruitAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/fruit/list").cookie(fruitCookie))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0]").value("사과"))
				.andExpect(jsonPath("$[1]").value("배"));
	}

	@Test
	public void testGetFruitListOfTokenIsNull() throws Exception {
		// Given
		List<String> expected = new ArrayList<>();
		expected.add("사과");
		expected.add("배");

		//When
		String fruitAccessToken = "fruitAccessTokenTest1";
		Cookie fruitCookie = new Cookie("fruitAccessToken", fruitAccessToken);

		when(greengroceryService.getCookie(Category.FRUIT)).thenReturn(fruitCookie);
		when(greengroceryService.getFruitList(fruitAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/fruit/list"))
				.andExpect(status().isOk())
				.andExpect(cookie().value("fruitAccessToken", fruitAccessToken))
				.andExpect(jsonPath("$[0]").value("사과"))
				.andExpect(jsonPath("$[1]").value("배"));
	}

	@Test
	public void testGetFruitPrice() throws Exception {
		// Given
		String name = "사과";
		String expected = "1000";
		String fruitAccessToken = "fruitAccessTokenTest";
		Cookie fruitCookie = new MockCookie("fruitAccessToken", fruitAccessToken);

		//When
		when(greengroceryService.getFruitPrice(name, fruitAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/fruit/price").cookie(fruitCookie).param("name", name))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data").value(expected));
	}

	@Test
	public void testGetFruitPriceOfNameIsNull() throws Exception {
		// Given
		String name = "사과";
		String expected = "1000";
		String fruitAccessToken = "fruitAccessTokenTest";
		Cookie fruitCookie = new MockCookie("fruitAccessToken", fruitAccessToken);

		//When
		when(greengroceryService.getFruitPrice(name, fruitAccessToken)).thenReturn(expected);
		// Then
		mockMvc.perform(get("/fruit/price").cookie(fruitCookie))
				.andExpect(jsonPath("$.success").value(false))
				.andExpect(jsonPath("$.errorCode").value(1))
				.andExpect(jsonPath("$.data").value("NAME_NULL"))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetFruitPriceOfTokenIsNull() throws Exception {
		// Given
		String name = "사과";
		String expected = "1000";

		//When
		String fruitAccessToken = "fruitAccessTokenTest1";
		Cookie fruitCookie = new Cookie("fruitAccessToken", fruitAccessToken);

		when(greengroceryService.getCookie(Category.FRUIT)).thenReturn(fruitCookie);
		when(greengroceryService.getFruitPrice(name, fruitAccessToken)).thenReturn(expected);

		// Then
		mockMvc.perform(get("/fruit/price").param("name", name))
				.andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.errorCode").value(0))
				.andExpect(jsonPath("$.data").value(expected))
				.andExpect(cookie().value("fruitAccessToken", fruitAccessToken))
				.andExpect(status().isOk());
	}

}
