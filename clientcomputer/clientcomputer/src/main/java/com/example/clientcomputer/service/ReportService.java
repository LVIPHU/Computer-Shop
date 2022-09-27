package com.example.clientcomputer.service;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

public interface ReportService {

	List<Object[]> getSalesByProduct(HttpSession session);

	List<Object[]> getSalesByTime(LocalDate startDate, LocalDate endDate, HttpSession session);
	
}
