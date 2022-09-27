package com.example.servercomputer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Category;
import com.example.servercomputer.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	Page<Product> findByCategory(Category category, Pageable pageable);
//	Page<Product> findAll(Pageable pageable);
	
	@Query(value = "SELECT * FROM Product p WHERE p.name LIKE :name", nativeQuery = true)
	List<Product> findByNameContaining(@Param("name") String name);

	List<Product> findAllByCategory(Category category);
	
	@Modifying
	@Query("Update Product p set p.quantity = :quantity where p.id= :id")
	void updateQuantity(@Param(value = "quantity") Integer quantity, @Param("id") Long id);
}
