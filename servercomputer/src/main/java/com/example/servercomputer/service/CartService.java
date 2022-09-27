package com.example.servercomputer.service;

import java.util.List;

import com.example.servercomputer.dto.CartDTO;
import com.example.servercomputer.entity.User;

public interface CartService {

	List<CartDTO> getCartByUserId(Long userId);

	CartDTO save(CartDTO cart);
	
	boolean deleteItem(Long productId, Long userId);

	void deleteAfterTransaction(User user);

	List<CartDTO> getCartByUserMail(String userMail);
	
	

}
