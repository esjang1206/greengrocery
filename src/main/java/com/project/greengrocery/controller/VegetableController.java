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
public class VegetableController {
	@Autowired
	GreengroceryService greengroceryService;

	/**
	 * 채소 목록 조회
	 * @param vegetableAccessToken
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/vegetable/list", method = RequestMethod.GET)
	public List getVegetableList(@CookieValue(name = "vegetableAccessToken", defaultValue = "") String vegetableAccessToken,
			HttpServletResponse response) {

		if(vegetableAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.VEGETABLE);
			response.addCookie(cookie);
			vegetableAccessToken = cookie.getValue();
		}
		return greengroceryService.getVegetableList(vegetableAccessToken);
	}

	/**
	 * 채소 가격 조회
	 * @param name
	 * @param vegetableAccessToken
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/vegetable/price", method = RequestMethod.GET)
	public AjaxModel getFruitPrice(
			@RequestParam(name = "name", defaultValue = "") String name,
			@CookieValue(name = "vegetableAccessToken", defaultValue = "") String vegetableAccessToken,
			HttpServletResponse response){

		if(name.isEmpty()){
			return new AjaxModel(false, GreenGroceryErrorCode.NAME_NULL.getCode(), GreenGroceryErrorCode.NAME_NULL);
		}
		if(vegetableAccessToken.isEmpty()){
			Cookie cookie = greengroceryService.getCookie(Category.VEGETABLE);
			response.addCookie(cookie);
			vegetableAccessToken = cookie.getValue();
		}
		return new AjaxModel(true, greengroceryService.getVegetablePrice(name, vegetableAccessToken));

	}
}
