package com.example.servercomputer.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.servercomputer.dto.CartDTO;
import com.example.servercomputer.entity.Cart;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.entity.entitypk.CartPK;
import com.example.servercomputer.repository.CartRepository;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	private CartRepository cartRepo;
	private UserRepository userRepo;
	private ModelMapper mapper;

	public CartServiceImpl(CartRepository cartRepo, UserRepository userRepo, ModelMapper mapper) {
		super();
		this.cartRepo = cartRepo;
		this.userRepo = userRepo;
		this.mapper = mapper;
	}

	@Override
	public List<CartDTO> getCartByUserId(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalStateException("Not found User"));
		return cartRepo.findByUser(user).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public List<CartDTO> getCartByUserMail(String userMail) {
		User user = userRepo.findByEmail(userMail).orElseThrow(() -> new IllegalStateException("Not found User"));
		return cartRepo.findByUser(user).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public CartDTO save(CartDTO cart) {
		CartPK cartPK = new CartPK(cart.getProductId(),cart.getUserId());
		Cart oldCart = cartRepo.findById(cartPK).orElse(null);
		if(oldCart!=null) {
			oldCart.setQuantity(cart.getQuantity());
			return toDTO(cartRepo.save(oldCart));
		}
		return toDTO(cartRepo.save(toEntity(cart)));
	}

	@Override
	public boolean deleteItem(Long productId, Long userId) {
		CartPK cartPK = new CartPK(productId, userId);
		Cart cart = cartRepo.findById(cartPK).orElseThrow(()-> new IllegalStateException("Not Found Cart Item"));
		cartRepo.delete(cart);
		return true;
	}
	
	@Override
	public void deleteAfterTransaction(User user) {
		cartRepo.deleteAllByUser(user);
	}

	private Cart toEntity(CartDTO dto) {
		return mapper.map(dto, Cart.class);
	}
	
	private CartDTO toDTO(Cart cart) {
		return mapper.map(cart, CartDTO.class);
	}
}