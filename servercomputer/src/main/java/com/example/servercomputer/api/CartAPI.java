package com.example.servercomputer.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.dto.CartDTO;
import com.example.servercomputer.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartAPI {
	private CartService cartService;

	public CartAPI(CartService cartService) {
		super();
		this.cartService = cartService;
	}

	@GetMapping("/{userMail}")
	public List<CartDTO> getCart(@PathVariable String userMail) {
		return cartService.getCartByUserMail(userMail);
	}
	
	@PostMapping
	public CartDTO addToCart(@Valid @RequestBody CartDTO cart) {
		return cartService.save(cart);
	}
	
	@PutMapping
	public CartDTO editItem(@Valid @RequestBody CartDTO cart) {
		return cartService.save(cart);
	}
	
	@DeleteMapping
	public boolean deleteItem(@RequestParam Long productId,
							@RequestParam Long userId) {
		return cartService.deleteItem(productId, userId);
	}
}
