package com.example.servercomputer.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.servercomputer.entity.entityenum.EStatusBill;

import lombok.Data;

@Data
public class BillDTO {
	private Long id;
	private LocalDateTime createDate;
	private EStatusBill status;
	private Long amount;
	private String note;
	private String receivedAddress;
	private String receivedPhoneNo;
	private Long userId;
	private String payment;
	private List<BillDetailDTO> billDetails;
}
