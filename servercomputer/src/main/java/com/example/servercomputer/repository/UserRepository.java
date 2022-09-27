package com.example.servercomputer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmailAndPassword(String email, String password);

	Optional<User> findByEmail(String username);
	
	User findByResetPassToken(String token);
}
