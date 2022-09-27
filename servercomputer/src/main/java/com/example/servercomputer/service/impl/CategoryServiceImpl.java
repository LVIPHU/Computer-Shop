package com.example.servercomputer.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.servercomputer.entity.Category;
import com.example.servercomputer.repository.CategoryRepository;
import com.example.servercomputer.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	private CategoryRepository categoryRepo;
	
	public CategoryServiceImpl(CategoryRepository categoryRepo) {
		this.categoryRepo = categoryRepo;
	}

	@Override
	public List<Category> findAll() {
		return categoryRepo.findAll();
	}

	@Override
	public Category findOneById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepo.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
	}

	@Override
	public Category save(Category category) {
		if(category.getId()!=null) {
			Category oldCate = categoryRepo.findById(category.getId()).orElseThrow(()-> new IllegalStateException("Not Found Category"));
			oldCate.setName(category.getName());
			return categoryRepo.save(oldCate);
		}
		return categoryRepo.save(category);
	}

	@Override
	public boolean delete(Long id) {
		categoryRepo.findById(id).orElseThrow(()-> new IllegalStateException("Not Found Category"));
		categoryRepo.deleteById(id);
		return true;
	}

}
