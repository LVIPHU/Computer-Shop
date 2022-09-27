package com.example.servercomputer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Cart;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.entity.entitypk.CartPK;

@Repository
public interface CartRepository extends JpaRepository<Cart, CartPK>{

	List<Cart> findByUser(User user);

	void deleteAllByUser(User user);
}
