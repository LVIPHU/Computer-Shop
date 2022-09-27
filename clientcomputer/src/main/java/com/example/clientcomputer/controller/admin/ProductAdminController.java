package com.example.clientcomputer.controller.admin;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.clientcomputer.model.Category;
import com.example.clientcomputer.model.Product;
import com.example.clientcomputer.service.CategoryService;
import com.example.clientcomputer.service.ProductService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/")
@AllArgsConstructor
public class ProductAdminController {
	private RestTemplate restTemplate;
	private ProductService productService;
	private CategoryService categoryService;

	@GetMapping("/admin-product")
	public String getProductAdmin(@RequestParam(required = false) Integer page, Model model) {
		if (page == null) {
			page = 0;
		}
		Map<String, Object> productPage = productService.findAll(page);
		model.addAttribute("products", productPage.get("content"));
		int totalPages = Integer.valueOf(productPage.get("totalPages").toString());
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", productPage.get("number"));
		model.addAttribute("totalElements", productPage.get("totalElements"));
		model.addAttribute("size", productPage.get("size"));
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "admin/product";
	}

	@GetMapping("/admin-product-add")
	public String getAddProduct(Model model) {
		model.addAttribute("product", new Product());
		return "admin/addproduct";
	}

	@PostMapping("/admin-product-add")
	public String addProduct(@ModelAttribute("product") Product product,
			@RequestParam("image_link") MultipartFile imageLink, HttpSession session) {
		return productService.save(product, imageLink, session);
	}

	@GetMapping("/admin-product-edit")
	public String editProduct(@RequestParam("id") Long id, Model model) {
		ResponseEntity<Product> responseEntity = restTemplate.getForEntity("/product/" + id, Product.class);
		model.addAttribute("product", responseEntity.getBody());
		return "admin/addproduct";
	}

	@GetMapping("/admin-product-delete/{id}")
	public String deleteProduct(@PathVariable("id") Long id) {
		restTemplate.delete("/product/" + id, id);
		return "admin/product";
	}

	@GetMapping("/admin-category")
	public String getCategory(Model model) {
		model.addAttribute("categoryNew", new Category());
		return "admin/category";
	}

	@PostMapping("/admin-category")
	public String addCategory(@ModelAttribute("categoryNew") Category category, HttpSession session) {
		return categoryService.saveCategory(category, session);
	}

	@GetMapping("/admin-category-delete/{id}")
	public String deleteCategory(@PathVariable Long id, HttpSession session) {
		if (!categoryService.deleteCategory(id, session)) {
			return "common/500error";
		}
		return "redirect:/admin/admin-category";
	}

	@GetMapping("/admin-category-add")
	public String getAddCategory() {
		return "admin/addcategory";
	}
}
