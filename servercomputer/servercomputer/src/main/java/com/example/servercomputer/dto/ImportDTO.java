package com.example.servercomputer.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ImportDTO {
	private Long id;
	private Long userId;
	private String userFirstName;
	private String userLastName;
	private LocalDateTime createdDate;
	private String note;
	private List<ImportDetailDTO> importDetails;
}
