package com.example.servercomputer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
