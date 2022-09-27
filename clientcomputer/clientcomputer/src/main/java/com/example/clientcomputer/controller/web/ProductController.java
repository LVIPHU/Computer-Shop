package com.example.clientcomputer.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.model.Product;
import com.example.clientcomputer.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
	private ProductService productService;
	
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}

	@GetMapping
	public String getAll(@RequestParam(name = "categoryId", required = false) Long id,
			@RequestParam(name="page",required = false) Integer page, Model model) {
		Map<String, Object> productPage = new HashMap<>();
		if(page==null) {
			page=0;
		}
		if(id!=null){
			productPage = productService.findByCategoryId(id,page);
		}else {
			productPage = productService.findAll(page);
		}
		model.addAttribute("products", productPage.get("content"));
		int totalPages = Integer.valueOf(productPage.get("totalPages").toString());
		
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", productPage.get("number"));
		model.addAttribute("totalElements", productPage.get("totalElements"));
		model.addAttribute("size", productPage.get("size"));
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
                .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
		return "web/store";
	}
	
	@GetMapping("/detail/{id}")
	public String getOneProduct(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		if(product==null) {
			return "common/500error";
		}
		model.addAttribute("product", product);
		model.addAttribute("productRelated", productService.findRelatedProduct(product.getCategoryId()));
		return "web/product";
	}
	
	@GetMapping("/search")
	public String getFilteredProduct(@RequestParam String keyword,
									@RequestParam(required = false) Long categoryId,
									HttpSession session,
									Model model) {
		if(categoryId == null) {
			categoryId = 0L;
		}
		model.addAttribute("products", productService.findByKeyword(keyword, categoryId, session));
		return "web/store";
	}
}
