package com.example.servercomputer.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.servercomputer.dto.ImportDTO;
import com.example.servercomputer.dto.ImportDetailDTO;
import com.example.servercomputer.entity.Import;
import com.example.servercomputer.entity.ImportDetail;
import com.example.servercomputer.entity.Product;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.repository.ImportDetailRepository;
import com.example.servercomputer.repository.ImportRepository;
import com.example.servercomputer.repository.ProductRepository;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.service.ImportService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ImportServiceImpl implements ImportService {
	private ImportRepository importRepo;
	private UserRepository userRepo;
	private ImportDetailRepository importDetailRepo;
	private ProductRepository productRepo;
	private ModelMapper modelMapper;

	@Override
	public List<ImportDTO> getAll() {
		return importRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public ImportDTO save(ImportDTO import1) {
		if(import1.getId()!=null) {
			Import oldImport = importRepo.findById(import1.getId()).orElseThrow(()->new IllegalStateException("Not Found Import"));
			oldImport.setNote(import1.getNote());
			return toDTO(importRepo.save(oldImport));
		}
		return toDTO(importRepo.save(toEntity(import1)));
	}

	@Override
	public boolean delete(Long id) {
		Import import1 = importRepo.findById(id).orElseThrow(()->new IllegalStateException("Not Found Import"));
		List<ImportDetail> details = importDetailRepo.findByImportId(import1);
		if(details.isEmpty()) {
			importRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<ImportDTO> getByUserId(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(()->new IllegalStateException("Not Found User"));
		return importRepo.findByUser(user).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	@Transactional
	@Override
	public List<ImportDetailDTO> saveDetails(List<ImportDetailDTO> detailDTOs) {
		List<ImportDetail> details = detailDTOs.stream().map(this::toEntityDetail).collect(Collectors.toList());
		details.forEach(t -> {
			Product product = productRepo.findById(t.getProduct().getId()).orElse(null);
			Integer quantity = (product.getQuantity() + t.getQuantity());
			productRepo.updateQuantity(quantity, product.getId());
		});
		return importDetailRepo.saveAll(details).stream().map(this::toDTODetail).collect(Collectors.toList());
	}

	private ImportDTO toDTO(Import entity) {
		return modelMapper.map(entity, ImportDTO.class);
	}
	
	private Import toEntity(ImportDTO dto) {
		return modelMapper.map(dto, Import.class);
	}
	
	private ImportDetailDTO toDTODetail(ImportDetail entity) {
		return modelMapper.map(entity, ImportDetailDTO.class);
	}
	
	private ImportDetail toEntityDetail(ImportDetailDTO dto) {
		return modelMapper.map(dto, ImportDetail.class);
	}
}
