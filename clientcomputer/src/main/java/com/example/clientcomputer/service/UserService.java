package com.example.clientcomputer.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.clientcomputer.model.UserLogin;
import com.example.clientcomputer.model.Users;

public interface UserService {

	String signUp(Users users);

	String login(UserLogin users, HttpSession session);

	Users findById(Long id, HttpSession session);

	List<Users> findAllUsers(HttpSession session);

	Users findByEmail(String userMail, HttpSession session);

	void updateInformation(Long id, Users user, HttpSession session);

	boolean checkPassword(String userMail, String oldPass, HttpSession session);

	void changePassword(String userMail, String newPass, HttpSession session);

	void createResetToken(String email, String token);

	Users getUserByToken(String token);

	boolean resetPassword(String userMail, String newPassword);

}
