package com.example.clientcomputer.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.example.clientcomputer.model.enums.EUser;

@Component
public class UrlFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String url = request.getRequestURI();
		if (url.contains("/admin/")) {
			String token = (String) request.getSession().getAttribute("token");
			if (token != null) {
				String role = VerifyJWT.checkRole(token).replace("ROLE_", "");
				if (role.equals(EUser.ADMIN.name())) {
					filterChain.doFilter(request, response);
				} else if (role.equals(EUser.USER.name())) {
					response.sendRedirect("/");
				}
			} else {
				response.sendRedirect("/login");
			}
		} else {
			filterChain.doFilter(request, response);
		}

	}

}
