package com.example.servercomputer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servercomputer.entity.Import;
import com.example.servercomputer.entity.ImportDetail;
import com.example.servercomputer.entity.entitypk.ImportDetailPK;

public interface ImportDetailRepository extends JpaRepository<ImportDetail, ImportDetailPK> {

	List<ImportDetail> findByImportId(Import importId);

}
