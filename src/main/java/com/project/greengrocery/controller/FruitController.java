package com.project.greengrocery.controller;

import com.project.greengrocery.model.AjaxModel;
import com.project.greengrocery.model.Category;
import com.project.greengrocery.model.GreenGroceryErrorCode;
import com.project.greengrocery.service.GreengroceryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class FruitController {
	@Autowired
	GreengroceryService greengroceryService;

	/**
	 * 과일 목록 조회
	 * @param fruitAccessToken
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fruit/list", method = RequestMethod.GET)
	public List getFruitList(@CookieValue(name = "fruitAccessToken", defaultValue = "") String fruitAccessToken,
			HttpServletResponse response) {

		if(fruitAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.FRUIT);
			response.addCookie(cookie);
			fruitAccessToken = cookie.getValue();
		}
		return greengroceryService.getFruitList(fruitAccessToken);
	}

	/**
	 * 과일 가격 조회
	 * @param name
	 * @param fruitAccessToken
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fruit/price", method = RequestMethod.GET)
	public AjaxModel getFruitPrice(
			@RequestParam(name = "name", defaultValue = "") String name,
			@CookieValue(name = "fruitAccessToken", defaultValue = "") String fruitAccessToken,
			HttpServletResponse response){

		if(name.isEmpty()){
			return new AjaxModel(false, GreenGroceryErrorCode.NAME_NULL.getCode(), GreenGroceryErrorCode.NAME_NULL);
		}
		if(fruitAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.FRUIT);
			response.addCookie(cookie);
			fruitAccessToken = cookie.getValue();
		}
		return new AjaxModel(true, greengroceryService.getFruitPrice(name, fruitAccessToken));
	}
}
