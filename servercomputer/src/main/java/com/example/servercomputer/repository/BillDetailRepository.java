package com.example.servercomputer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Bill;
import com.example.servercomputer.entity.BillDetail;
import com.example.servercomputer.entity.entitypk.BillDetailPK;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, BillDetailPK>{

	List<BillDetail> findAllByBill(Bill bill);

}
