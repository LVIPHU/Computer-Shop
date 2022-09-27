package com.example.clientcomputer.service.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.model.Import;
import com.example.clientcomputer.model.ImportDetail;
import com.example.clientcomputer.service.ImportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImportServiceImpl implements ImportService{
	private RestTemplate restTemplate;
	
	@Override
	public void save(Import importObject, HttpSession session) {
		List<ImportDetail> details = importObject.getImportDetails();
		importObject.setImportDetails(null);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Import> entity = new HttpEntity<Import>(importObject, headers);
		ResponseEntity<Import> responseEntity = restTemplate.postForEntity("/import", entity, Import.class);
		
		details.forEach(t -> t.setImportIdId(responseEntity.getBody().getId()));
		HttpEntity<List<ImportDetail>> httpEntityDetail = new HttpEntity<>(details, headers);
		ResponseEntity<List<ImportDetail>> responseEntityDetail = restTemplate.exchange("/import/detail", HttpMethod.POST, httpEntityDetail, new ParameterizedTypeReference<List<ImportDetail>>() {});
		
	}
}
