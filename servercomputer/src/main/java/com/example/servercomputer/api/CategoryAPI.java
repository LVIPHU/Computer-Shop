package com.example.servercomputer.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.entity.Category;
import com.example.servercomputer.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryAPI {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping()
	public List<Category> getList(){
		return categoryService.findAll();
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
	public Category save(@Valid @RequestBody Category category) {
		return categoryService.save(category);
	}
	
	@PutMapping
	public Category update(@Valid @RequestBody Category category) {
		return categoryService.save(category);
	}
	
	@DeleteMapping("/{id}")
	public boolean delete(@PathVariable Long id) {
		return categoryService.delete(id);
	}
	
}
