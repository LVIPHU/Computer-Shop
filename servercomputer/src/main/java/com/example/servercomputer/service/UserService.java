package com.example.servercomputer.service;

import java.util.List;

import com.example.servercomputer.dto.UserDTO;

public interface UserService {
	public List<UserDTO> findAll();
	
	public UserDTO findOneById(Long id);
	
	public UserDTO save(UserDTO user);
	
	public boolean delete(Long id);

	public UserDTO login(UserDTO user);

	public UserDTO changePassword(UserDTO user);

	public UserDTO findByEmail(String userMail);
	
	public void createResetToken(String token, String email);
	
	public UserDTO findByResetToken(String token);
	
	public void updateNewPassword(String userMail, String newPassword);
}
