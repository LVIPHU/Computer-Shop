package com.example.servercomputer.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.dto.ImportDTO;
import com.example.servercomputer.dto.ImportDetailDTO;
import com.example.servercomputer.service.ImportService;

@RestController
@RequestMapping("/api/import")
public class ImportAPI {
	private ImportService importService;

	public ImportAPI(ImportService importService) {
		super();
		this.importService = importService;
	}

	@GetMapping
	public List<ImportDTO> getImports(){
		return importService.getAll(); 
	}
	
	@GetMapping("/{userId}")
	public List<ImportDTO> getImportsByUserId(@PathVariable Long userId){
		return importService.getByUserId(userId); 
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ImportDTO importProduct(@Valid @RequestBody ImportDTO import1) {
		return importService.save(import1);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ImportDTO editImport(@Valid @RequestBody ImportDTO import1, @PathVariable Long id) {
		import1.setId(id);
		return importService.save(import1);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public boolean deleteImport(@PathVariable Long id) {
		return importService.delete(id);
	}
	
	@PostMapping("/detail")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<ImportDetailDTO> saveDetailDTOs(@RequestBody List<ImportDetailDTO> detailDTOs){
		return importService.saveDetails(detailDTOs);
	}
	
}
