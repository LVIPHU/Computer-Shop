package com.example.servercomputer.auth;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.servercomputer.entity.User;
import com.example.servercomputer.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService{
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> userOpt = userRepository.findByEmail(email);
		
		if (userOpt.isEmpty()) {
			System.out.println("Email not found! " + email);
			throw new UsernameNotFoundException("Email: " + email + " was not found in the database");
		}
		
		ApplicationUser applicationUser = new ApplicationUser(userOpt.get());
		
		return applicationUser;
	}

}
