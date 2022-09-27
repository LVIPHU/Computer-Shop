package com.example.clientcomputer.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.service.ReportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
	private RestTemplate restTemplate;

	@Override
	public List<Object[]> getSalesByProduct(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Object[]>> httpEntity = new HttpEntity<List<Object[]>>(headers);
		ResponseEntity<List<Object[]>> responseEntity = restTemplate.exchange("/report/product", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Object[]>>() {});
		
		return responseEntity.getBody();
	}
	
	@Override
	public List<Object[]> getSalesByTime(LocalDate startDate, LocalDate endDate, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Object[]>> httpEntity = new HttpEntity<List<Object[]>>(headers);
		ResponseEntity<List<Object[]>> responseEntity = restTemplate.exchange("/report/time?startDate="+startDate.format(DateTimeFormatter.ISO_DATE)+"&endDate="+endDate.format(DateTimeFormatter.ISO_DATE),
							HttpMethod.GET, 
							httpEntity, 
							new ParameterizedTypeReference<List<Object[]>>() {});
		
		return responseEntity.getBody();
	}

}
