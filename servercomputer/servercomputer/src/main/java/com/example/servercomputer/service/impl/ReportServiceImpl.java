package com.example.servercomputer.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.servercomputer.repository.BillRepository;
import com.example.servercomputer.service.ReportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{
	private BillRepository billRepository;

	@Override
	public List<Object[]> getReportByProduct() {
		return billRepository.getReportByProduct();
	}

	@Override
	public List<Object[]> getReportByTime(LocalDate startDate, LocalDate endDate) {
		return billRepository.getReportByTime(startDate, endDate);
	}

}
