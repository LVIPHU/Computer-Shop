package com.example.clientcomputer.model;

import java.time.LocalDateTime;
import java.util.List;

import com.example.clientcomputer.model.enums.EStatusBill;

import lombok.Data;

@Data
public class Bill {
	private Long id;
	private LocalDateTime createDate;
	private EStatusBill status;
	private Long amount;
	private String note;
	private String receivedAddress;
	private String receivedPhoneNo;
	private Long userId;
	private String payment;
	private List<BillDetail> billDetails;
}
