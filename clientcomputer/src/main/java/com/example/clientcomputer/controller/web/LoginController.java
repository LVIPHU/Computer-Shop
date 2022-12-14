package com.example.clientcomputer.controller.web;

import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.mail.EmailDetail;
import com.example.clientcomputer.mail.EmailService;
import com.example.clientcomputer.model.UserLogin;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class LoginController {
	private UserService userService;
	private EmailService emailService;

	@GetMapping("/login")
	public String getLogin(Model model) {
		model.addAttribute("userLogin", new UserLogin());
		return "web/login";
	}
	
	@GetMapping("/register")
	public String getRegister(Model model) {
		model.addAttribute("userSignup", new Users());
		return "web/register";
	}
	
	@PostMapping("/signup")
	public String signup(@Valid @ModelAttribute("userSignup") Users userSignup,
						BindingResult result) {
		if (result.hasErrors()) {
		    return "web/register";
		}
		return userService.signUp(userSignup);
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("userLogin") UserLogin userLogin,
						BindingResult result,
						HttpSession session) {
		if (result.hasErrors()) {
		    return "web/login";
		}
		return userService.login(userLogin, session);
	}
	
	@GetMapping("/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@GetMapping("/reset-password")
	public String getResetPass(@RequestParam String token, 
							@RequestParam(required = false) String message, 
							Model model,
							HttpSession session) {
		if(message != null) {
			model.addAttribute("message", message);
		}
		if(token==null) {
			return "redirect:/";
		}
		if(userService.getUserByToken(token)==null) {
			return "redirect:/";
		}
		session.setAttribute("tokenReset", token);
		return "web/reset-password";
	}
	
	@PostMapping("/reset-password")
	public String resetPass(@RequestParam String newPassword,
							@RequestParam String rePassword,
							HttpSession session) {
		String token = (String) session.getAttribute("tokenReset");
		String userMail = token.split("_")[0];
		if(!newPassword.equals(rePassword)) {
			return "redirect:/reset-password?token="+token +"&message=password-not-match";
		}
		if(!userService.resetPassword(userMail, newPassword)) {
			return "redirect:/reset-password?token="+token +"&message=doi-mat-khau-that-bai";
		}
		session.removeAttribute("tokenReset");
		return "redirect:/login";
	}
	
	@GetMapping("/forgot-password")
	public String getForgotPass() {
		return "web/forget-pass";
	}
	
	@PostMapping("/forgot-password")
	public String forgotPass(@RequestParam String email) {
		String token = UUID.randomUUID().toString();
		try {
			userService.createResetToken(email, token);	
			
			sendResetPasswordEmail(email, token);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/forgot-password";
		}
		return "redirect:/";
	}
	
	void sendResetPasswordEmail(String email, String token) {
		token = email+"_"+token;
		String content = "<p>Hello, </p>"
				+ "<p>B???n c?? y??u c???u c???p l???i m???t kh???u.</p>"
				+ "<p>Vui l??ng nh???p v??o ???????ng d???n b??n d?????i ????? ?????i m???t kh???u</p>"
				+ "<p><b><a href=\"http://localhost:8080/reset-password?token="+ token +"\"> Nh???p v??o ????y</a><b></p>"
				+ "<p>B??? qua mail n??y n???u b???n nh??? m???t kh???u ho???c kh??ng ph???i b???n y??u c???u</p>";
		EmailDetail detail = EmailDetail
				.builder()
				.recipient(email)
				.msgBody(content)
				.subject("Y??U C???U X??C TH???C C???P L???I M???T KH???U")
				.build();
		emailService.sendSimpleMail(detail);
	}
}
