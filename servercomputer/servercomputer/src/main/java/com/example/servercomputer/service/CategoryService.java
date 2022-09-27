package com.example.servercomputer.service;

import java.util.List;

import com.example.servercomputer.entity.Category;

public interface CategoryService {
	public List<Category> findAll();
	
	public Category findOneById(Long id);
	
	public Category save(Category category);
	
	public boolean delete(Long id);
}
