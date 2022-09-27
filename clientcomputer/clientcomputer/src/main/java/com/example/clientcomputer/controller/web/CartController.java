package com.example.clientcomputer.controller.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.model.Cart;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.ProductService;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("cart")
@AllArgsConstructor
public class CartController {
	private ProductService productService;
	private UserService userService;
	
	@GetMapping
	public String getCart(Model model, HttpSession session) {
		String userMail = (String) session.getAttribute("userMail");
		List<Cart> carts = productService.getCartByUserMail(userMail, session);
		if(carts==null) {
			return "common/500error";
		}
		Long amount=0L;
		Integer totalItems=0;
		for (Cart cart : carts) {
			amount = (long) (amount + (cart.getQuantity()*cart.getPrice()));
			totalItems = totalItems + cart.getQuantity();
		};
		model.addAttribute("amount", amount);
		model.addAttribute("totalCartItems", totalItems);
		model.addAttribute("cartItems", carts);
		
		return "web/cart";
	}
	
	@PostMapping
	public String addCart(@RequestParam Long productId, HttpSession session) {
		return productService.addToCart(productId, session);
	}
	
	@GetMapping("/delete/{productId}")
	public String deleteCartItem(@PathVariable Long productId, HttpSession session) {
		String userMail = (String) session.getAttribute("userMail");
		Users user = userService.findByEmail(userMail, session);
		return productService.deleteItem(productId, user.getId(), session);
	}
	
	@GetMapping("/update")
	public String updateQuantity(@RequestParam Long productId,
								@RequestParam Integer quantity) {
		Long userId = 1L;
		if(quantity > productService.findById(productId).getQuantity()) {
			return "redirect:/cart";
		}
		return productService.updateQuantityItem(productId, userId, quantity);
	}
}
