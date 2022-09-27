package com.example.servercomputer.service;

import java.util.List;

import com.example.servercomputer.dto.ImportDTO;
import com.example.servercomputer.dto.ImportDetailDTO;

public interface ImportService {

	List<ImportDTO> getAll();

	ImportDTO save(ImportDTO import1);

	boolean delete(Long id);

	List<ImportDTO> getByUserId(Long userId);

	List<ImportDetailDTO> saveDetails(List<ImportDetailDTO> detailDTOs);

}
