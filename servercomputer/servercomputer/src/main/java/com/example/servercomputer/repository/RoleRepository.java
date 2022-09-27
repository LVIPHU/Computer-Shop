package com.example.servercomputer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
