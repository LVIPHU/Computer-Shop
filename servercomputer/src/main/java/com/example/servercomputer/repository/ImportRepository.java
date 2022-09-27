package com.example.servercomputer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.servercomputer.entity.Import;
import com.example.servercomputer.entity.User;

public interface ImportRepository extends JpaRepository<Import, Long> {
	List<Import> findByUser(User user);
}
