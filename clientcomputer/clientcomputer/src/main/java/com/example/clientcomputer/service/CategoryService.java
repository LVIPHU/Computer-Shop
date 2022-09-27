package com.example.clientcomputer.service;

import javax.servlet.http.HttpSession;

import com.example.clientcomputer.model.Category;

public interface CategoryService {

	String saveCategory(Category category, HttpSession session);

	boolean deleteCategory(Long id, HttpSession session);

}
