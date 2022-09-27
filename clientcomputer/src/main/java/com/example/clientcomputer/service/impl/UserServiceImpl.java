package com.example.clientcomputer.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.config.VerifyJWT;
import com.example.clientcomputer.model.UserLogin;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.model.enums.EUser;
import com.example.clientcomputer.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private RestTemplate restTemplate;

	public UserServiceImpl(RestTemplate restTemplate) {
		super();
		this.restTemplate = restTemplate;
	}

	@Override
	public String signUp(Users users) {
		users.setRoleId((long) EUser.USER.getValue());
		HttpEntity<Users> requestBody = new HttpEntity<>(users);
		ResponseEntity<Users> responseEntity = restTemplate.postForEntity("/user", requestBody, Users.class);
		if (responseEntity.getStatusCode()==HttpStatus.OK) {
			return "redirect:login";
		} else {
			return "redirect:login";			
		}
	}

	@Override
	public String login(UserLogin users, HttpSession session) {
		HttpEntity<UserLogin> requestBody = new HttpEntity<>(users);
		ResponseEntity<String> responseEntity = null; 
		try {
			responseEntity = restTemplate.postForEntity("http://localhost:8081/login", requestBody, String.class);
		} catch (Exception e) {
			return "web/login?message=password not match";
		}
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			session.setAttribute("token", responseEntity.getHeaders().get("Authorization").get(0));
			VerifyJWT.verify(responseEntity.getHeaders().get("Authorization").get(0), session);
			if(session.getAttribute("role").equals("ROLE_ADMIN")) {
				return "redirect:/admin/";
			}
			return "redirect:/";
		}
		
		return "web/login?message=invalid";
	}

	@Override
	public Users findById(Long id, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Users> httpEntity = new HttpEntity<Users>(headers);
		ResponseEntity<Users> responseEntity = restTemplate.exchange("/user/"+id, HttpMethod.GET, httpEntity, Users.class);
		return responseEntity.getBody();
	}
	
	@Override
	public List<Users> findAllUsers(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Users>> requestBody = new HttpEntity<>(headers);
		ResponseEntity<List<Users>> responseEntity= restTemplate.exchange("/user", HttpMethod.GET, requestBody, new ParameterizedTypeReference<List<Users>>(){});
		return responseEntity.getBody();
	}

	@Override
	public Users findByEmail(String userMail, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Users> requestBody = new HttpEntity<>(headers);
		ResponseEntity<Users> responseEntity= restTemplate.exchange("/user/mail?userMail="+userMail, HttpMethod.GET, requestBody, Users.class);
		return responseEntity.getBody();
	}
	
	@Override
	public void updateInformation(Long id, Users user, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Users> requestBody = new HttpEntity<>(user, headers);
		restTemplate.put("/user/"+id, requestBody);
	}
	
	@Override
	public boolean checkPassword(String userMail, String oldPass, HttpSession session) {
		Users user = findByEmail(userMail, session);
		return BCrypt.checkpw(oldPass, user.getPassword());
	}
	
	@Override
	public void changePassword(String userMail, String newPass, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		Users users = Users.builder()
						.email(userMail)
						.password(newPass)
						.build();
		HttpEntity<Users> requestBody = new HttpEntity<>(users, headers);
		ResponseEntity<Users> responseEntity= restTemplate.exchange("/user/password", HttpMethod.PUT, requestBody, Users.class);
		responseEntity.getBody();
	}
	
	@Override
	public void createResetToken(String email, String token) {
		token = email + "_" + token;
		restTemplate.put("/user/resetToken?token="+token, null);
	}
	
	@Override
	public Users getUserByToken(String token) {
		token = token.split("_")[1];
		ResponseEntity<Users> responseEntity;
		try {
			responseEntity = restTemplate.getForEntity("/user/resetToken?token="+token,Users.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return responseEntity.getBody();
	}
	
	@Override
	public boolean resetPassword(String userMail, String newPassword) {
		try {
			restTemplate.put("/user/resetToken/"+userMail+"?password="+newPassword, null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
