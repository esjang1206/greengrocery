package com.project.greengrocery.controller;

import com.project.greengrocery.model.Category;
import com.project.greengrocery.service.GreengroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GreengroceryController {

	@Autowired
	GreengroceryService greengroceryService;
	/**
	 * 과일, 채소 access token 이 없으면 API 호출 후, cookie로 반환
	 *
	 * @param fruitAccessToken
	 * @param vegetableAccessToken
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(@CookieValue(name = "fruitAccessToken", defaultValue = "") String fruitAccessToken,
			@CookieValue(name = "vegetableAccessToken", defaultValue = "") String vegetableAccessToken,
			HttpServletResponse response) {

		if(fruitAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.FRUIT);
			response.addCookie(cookie);
		}
		if(vegetableAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.VEGETABLE);
			response.addCookie(cookie);
		}

		return "index";
	}
}
