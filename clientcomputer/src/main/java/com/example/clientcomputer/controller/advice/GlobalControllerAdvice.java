package com.example.clientcomputer.controller.advice;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.model.Cart;
import com.example.clientcomputer.model.Category;
import com.example.clientcomputer.model.Product;

@ControllerAdvice
public class GlobalControllerAdvice {
	@Autowired
	private RestTemplate restTemplate;
	
	@ModelAttribute("categories")
	public List<Category> getCategories(){
		ResponseEntity<List<Category>> entity = restTemplate.exchange("/category", HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {});
		List<Category> categories = entity.getBody().stream().map(t -> {
			ResponseEntity<List<Product>> productEntity = restTemplate.exchange("/product/category"+t.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
			t.setProductNo(productEntity.getBody().size());
			return t;
		}).toList();
		return categories;
	}
	
	@ModelAttribute(name = "products")
	public List<Product> getProducts(){
		ResponseEntity<List<Product>> entity = restTemplate.exchange("/product/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
		return entity.getBody();
	}
	
	@ModelAttribute(name = "carts")
	public List<Cart> getCarts(Model model, HttpSession session){
		String userMail = (String) session.getAttribute("userMail");
		ResponseEntity<List<Cart>> entity ;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Cart>> httpEntity = new HttpEntity<List<Cart>>(headers);
		try {
			entity = restTemplate.exchange("/cart/"+userMail, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Cart>>() {});			
		} catch (Exception e) {
			return null;
		}
		Long amount=0L;
		Integer totalItems=0;
		for (Cart cart : entity.getBody()) {
			amount = (long) (amount + (cart.getQuantity()*cart.getPrice()));
			totalItems = totalItems + cart.getQuantity();
		};
		model.addAttribute("amount", amount);
		model.addAttribute("totalCartItems", totalItems);
		
		return entity.getBody();
	}
}
