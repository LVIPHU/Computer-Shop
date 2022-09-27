package com.example.servercomputer.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.service.ReportService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/report")
@AllArgsConstructor
public class ReportAPI {
	private ReportService reportService;
	
	@GetMapping("/product")
	public List<Object[]> getReport(){
		return reportService.getReportByProduct();
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/time")
	public List<Object[]> getReportByTime(
							@RequestParam String startDate, 
							@RequestParam String endDate){
		LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
		return reportService.getReportByTime(start, end);
	}
}
