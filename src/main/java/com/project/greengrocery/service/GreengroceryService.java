package com.project.greengrocery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.greengrocery.model.Category;
import com.project.greengrocery.util.APIHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class GreengroceryService {

	/**
	 * 과일 access token 발급
	 *
	 * @return access token
	 */
	private String getFruitAccessToken() {
		String fruitGetTokenUrl = APIHttpRequest.FRUIT_URL + "/token";
		try {
			ResponseEntity<String> responseEntity = APIHttpRequest.requestGetForJson(fruitGetTokenUrl);
			ObjectMapper mapper = new ObjectMapper();
			HashMap<String, String> map = mapper.readValue(responseEntity.getBody(), HashMap.class);
			return map.get("accessToken");
		} catch (JsonProcessingException e) {
			log.info("FAIL TO GET ACCESS TOKEN", e.getMessage());
		}
		return null;
	}

	/**
	 * 채소 access token 발급
	 *
	 * @return access token
	 */
	private String getVegetableAccessToken() {
		String vegetableGetTokenUrl = APIHttpRequest.VEGETABLE_URL + "/token";
		ResponseEntity responseEntity = APIHttpRequest.requestGetForJson(vegetableGetTokenUrl);
		return responseEntity.getHeaders().get("Set-Cookie").toString().split("=")[1].split(";")[0];
	}

	/**
	 * 과일 목록 반환
	 *
	 * @param accessToken
	 */
	public List getFruitList(String accessToken) {
		String fruitListGetUrl = APIHttpRequest.FRUIT_URL + "/product";
		return APIHttpRequest.requestGetForList(fruitListGetUrl, accessToken);
	}

	/**
	 * 과일 가격 조회
	 *
	 * @param name        과일명
	 * @param accessToken
	 */
	public String getFruitPrice(String name, String accessToken) {
		String fruitPriceGetUrl = APIHttpRequest.FRUIT_URL + "/product?name=" + name;
		return APIHttpRequest.requestGetForJson(fruitPriceGetUrl, accessToken);
	}


	/**
	 * 채소 목록 반환
	 *
	 * @param accessToken
	 */
	public List getVegetableList(String accessToken) {
		String vegetableListGetUrl = APIHttpRequest.VEGETABLE_URL + "/item";
		return APIHttpRequest.requestGetForList(vegetableListGetUrl, accessToken);
	}

	/**
	 * 채소 가격 조회
	 *
	 * @param name        채소명
	 * @param accessToken
	 */
	public String getVegetablePrice(String name, String accessToken) {
		String vegetablePriceGetUrl = APIHttpRequest.VEGETABLE_URL + "/item?name=" + name;
		return APIHttpRequest.requestGetForJson(vegetablePriceGetUrl, accessToken);
	}

	/**
	 * 과일/채소 access token을 cookie 반환
	 *
	 * @param category 과일(FRUIT)/채소(VEGETABLE)
	 * @return 과일/채소 access token 값
	 */
	public Cookie getCookie(Category category) {
		String tokenName = "";
		String token = "";
		if (category == Category.FRUIT) {
			tokenName = "fruitAccessToken";
			token = this.getFruitAccessToken();
		} else {
			tokenName = "vegetableAccessToken";
			token = this.getVegetableAccessToken();
		}
		Cookie cookie = new Cookie(tokenName, token);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(7200); //2시간
		return cookie;
	}

}
