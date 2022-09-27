package com.example.clientcomputer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.example.clientcomputer.model.Cart;
import com.example.clientcomputer.model.Product;

public interface ProductService {

	String save(Product product, MultipartFile imageLink, HttpSession session);

	Product findById(Long id);

	List<Product> findRelatedProduct(Long categoryId);

	Map<String, Object> findByCategoryId(Long categoryId, Integer page);
	
	Map<String, Object> findAll(Integer page);

	String addToCart(Long productId, HttpSession session);

	List<Cart> getCartByUserId(Long userId, HttpSession session);

	String deleteItem(Long productId, Long userId, HttpSession session);

	String updateQuantityItem(Long productId, Long userId, Integer quantity);
	
	Long getAmount(List<Cart> carts);

	List<Cart> getCartByUserMail(String userMail, HttpSession session);

	List<Product> findByKeyword(String keyword, Long categoryId, HttpSession session);

	List<Product> findAllHome();

}
