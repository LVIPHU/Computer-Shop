package com.example.servercomputer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.servercomputer.entity.Bill;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.entity.entityenum.EStatusBill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>{

	List<Bill> findByUser(User user);

	@Query(value = "SELECT p.name as 'productName', sum(d.quantity) as 'quantity', sum(d.price * d.quantity) as 'amount'\r\n"
			+ "FROM bill_detail d\r\n"
			+ "	JOIN bill b\r\n"
			+ "		ON b.id = d.bill_id\r\n"
			+ "	JOIN product p\r\n"
			+ "		ON p.id = d.product_id\r\n"
			+ "GROUP BY p.name;", nativeQuery = true)
	List<Object[]> getReportByProduct();
	
	@Query(value = "SELECT date_format(b.create_date, '%m-%Y') as 'Ngày', sum(b.amount) as 'Tổng tiền'\r\n"
			+ "FROM bill b\r\n"
			+ "WHERE b.create_date >= :startDate AND b.create_date <= :endDate\r\n"
			+ "GROUP BY date_format(b.create_date, '%m-%Y');", nativeQuery = true)
	List<Object[]> getReportByTime(@Param(value = "startDate") LocalDate startDate, @Param(value = "endDate") LocalDate endDate);

	@Modifying
	@Query("Update Bill b set b.status = :status where b.id= :id")
	void updateStatus(@Param(value = "status")EStatusBill statusBill, @Param("id") Long id);
}
