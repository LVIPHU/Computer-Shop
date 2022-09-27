package com.example.servercomputer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.servercomputer.dto.BillDTO;
import com.example.servercomputer.dto.BillDetailDTO;
import com.example.servercomputer.entity.Bill;
import com.example.servercomputer.entity.BillDetail;
import com.example.servercomputer.entity.Cart;
import com.example.servercomputer.entity.Product;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.entity.entityenum.EStatusBill;
import com.example.servercomputer.repository.BillDetailRepository;
import com.example.servercomputer.repository.BillRepository;
import com.example.servercomputer.repository.CartRepository;
import com.example.servercomputer.repository.ProductRepository;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.service.BillService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BillServiceImpl implements BillService {
	private UserRepository userRepo;
	private BillRepository billRepo;
	private BillDetailRepository billDetailRepo;
	private CartRepository cartRepo;
	private ProductRepository productRepo;
	private ModelMapper modelMapper;

	@Override
	public List<BillDTO> getBillByUser(Long userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new IllegalStateException("Not found User"));

		return billRepo.findByUser(user).stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public BillDTO save(BillDTO bill) {
		Bill newBill = billRepo.save(toEntity(bill));
		System.out.println(newBill.toString());
		List<Cart> carts = cartRepo.findByUser(newBill.getUser());
		List<BillDetail> billDetails = new ArrayList<BillDetail>();
		for (Cart cart : carts) {
			BillDetail detail = new BillDetail();
			Product product = cart.getProduct();
			detail.setBill(newBill);
			detail.setProduct(product);
			detail.setQuantity(cart.getQuantity());
			detail.setPrice(cart.getPrice());
			product.setQuantity(product.getQuantity()-detail.getQuantity());
			productRepo.save(product);
			billDetails.add(detail);
		}
		billDetailRepo.saveAll(billDetails);
		cartRepo.deleteAll(carts);
		
		return toDTO(newBill);
	}

	@Override
	public void deleteBill(Long id) {
		Bill oldBill = billRepo.findById(id).orElseThrow(() -> new IllegalStateException("Not found Bill"));
		oldBill.setStatus(EStatusBill.CANCELED);
		for (BillDetail detail : oldBill.getBillDetails()) {
			Product product = detail.getProduct();
			product.setQuantity(product.getQuantity() + detail.getQuantity());
			productRepo.save(product);
		}
		billRepo.save(oldBill);
	}
	
	@Override
	public List<BillDTO> getAllBill() {
		return billRepo.findAll().stream().map(this::toDTO).toList();
	}
	
	@Override
	public List<BillDTO> getByEmail(String userMail) {
		User user = userRepo.findByEmail(userMail).orElse(null);
		
		return billRepo.findByUser(user).stream().map(this::toDTO).toList();
	}
	
	@Transactional
	@Override
	public void approveBill(Long id, EStatusBill statusBill) {
		billRepo.updateStatus(statusBill, id);
	}
	
	@Override
	public List<BillDetailDTO> getBillDetailsByBillId(Long billId) {
		Bill bill = billRepo.findById(billId).orElse(null);
		return billDetailRepo.findAllByBill(bill).stream().map(t -> modelMapper.map(t, BillDetailDTO.class)).toList();
	}

	private Bill toEntity(BillDTO dto) {
		return modelMapper.map(dto, Bill.class);
	}

	private BillDTO toDTO(Bill bill) {
		return modelMapper.map(bill, BillDTO.class);
	}

	
}
