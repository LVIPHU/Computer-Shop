package com.example.clientcomputer.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.model.Category;
import com.example.clientcomputer.service.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	private RestTemplate restTemplate;
	
	@Override
	public String saveCategory(Category category, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Category> requestBody = new HttpEntity<>(category, headers);
		try {
			restTemplate.postForEntity("/category", requestBody, Category.class);		
			return "redirect:admin-category";
		} catch (RestClientException e) {
			return "common/500error";
		}
	}

	@Override
	public boolean deleteCategory(Long id, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Category> requestBody = new HttpEntity<>(headers);
		try {
			return restTemplate.exchange("/category/"+id, HttpMethod.DELETE, requestBody, Boolean.class).getBody();
		} catch (RestClientException e) {
			return false;
		}
	}

}
