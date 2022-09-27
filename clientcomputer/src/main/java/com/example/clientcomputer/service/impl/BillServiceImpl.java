package com.example.clientcomputer.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.clientcomputer.model.Bill;
import com.example.clientcomputer.model.BillDetail;
import com.example.clientcomputer.model.Cart;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.model.enums.EStatusBill;
import com.example.clientcomputer.service.BillService;
import com.example.clientcomputer.service.ProductService;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService{
	private ProductService productService;
	private RestTemplate restTemplate;
	private UserService userService;

	@Override
	public String saveBill(Bill bill, HttpSession session) {
		Users users = userService.findByEmail((String) session.getAttribute("userMail"), session);
		List<Cart> carts = productService.getCartByUserMail(users.getEmail(), session);
		bill.setUserId(users.getId());
		bill.setAmount(productService.getAmount(carts));
		bill.setCreateDate(LocalDateTime.now());
		bill.setStatus(EStatusBill.DRAFT);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Bill> httpEntity = new HttpEntity<Bill>(bill, headers);
		ResponseEntity<Bill> responseEntity = restTemplate.postForEntity("/bill", httpEntity, Bill.class);
		if(responseEntity.getStatusCode() == HttpStatus.OK) {
			return "redirect:/";
		}
		
		return "redirect:/checkout";
	}

	@Override
	public List<Bill> getBills(HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String)session.getAttribute("token"));
		HttpEntity<List<Bill>> httpEntity = new HttpEntity<List<Bill>>(headers);
		ResponseEntity<List<Bill>> responseEntity = restTemplate.exchange("/bill", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Bill>>() {});
		return responseEntity.getBody();
	}

	@Override
	public List<Bill> getBillByEmail(HttpSession session) {
		String userMail = (String) session.getAttribute("userMail");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String)session.getAttribute("token"));
		HttpEntity<List<Bill>> httpEntity = new HttpEntity<List<Bill>>(headers);
		ResponseEntity<List<Bill>> responseEntity = restTemplate.exchange("/bill/tracking?userMail="+userMail, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Bill>>() {});
		return responseEntity.getBody();
	}
	
	@Override
	public void changeStatusBill(Long id, EStatusBill statusBill, HttpSession session) {
		switch (statusBill) {
			case DRAFT:
				statusBill = EStatusBill.CONFIRMED;
				break;
			case CONFIRMED:
				statusBill = EStatusBill.PAID;
				break;
			case PAID:
				statusBill = EStatusBill.COMPLETED;
				break;
			default:
				break;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String)session.getAttribute("token"));
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		restTemplate.put("/bill/"+id +"?statusBill="+statusBill, httpEntity);
	}

	@Override
	public List<BillDetail> getBillDetailsByBillId(Long billId, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String)session.getAttribute("token"));
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<List<BillDetail>> responseEntity = restTemplate.exchange("/bill/tracking/"+billId, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<BillDetail>>() {});
		
		return responseEntity.getBody();
	}

	@Override
	public List<Bill> getBillFilterByEmailAndTime(LocalDateTime startDate, LocalDateTime endDate, HttpSession session) {
		List<Bill> bills = getBillByEmail(session);
		return bills.stream().filter(bill -> (bill.getCreateDate().isBefore(endDate)) && bill.getCreateDate().isAfter(startDate)).toList();
	}
}
