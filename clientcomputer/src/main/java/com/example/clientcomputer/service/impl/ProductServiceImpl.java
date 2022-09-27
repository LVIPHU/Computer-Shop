package com.example.clientcomputer.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.clientcomputer.model.Cart;
import com.example.clientcomputer.model.Product;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.ProductService;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private RestTemplate restTemplate;
	private UserService userService;

	@Override
	public String save(Product product, MultipartFile imageLink, HttpSession session) {
		try {
			product.setImage(Base64.getEncoder().encodeToString(imageLink.getBytes()));
			product.setQuantity(0);
		} catch (IOException e) {
			e.printStackTrace();
			return "redirect:admin-product-add";
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Product> httpEntity = new HttpEntity<>(product, headers);
		ResponseEntity<Product> responseEntity;
		if(product.getId()!=null) {
			responseEntity = restTemplate.exchange("/product",
										HttpMethod.PUT, httpEntity,
								        Product.class);
		} else {
			responseEntity = restTemplate.postForEntity("/product", httpEntity, Product.class);
		}
		if(responseEntity.getStatusCode()==HttpStatus.OK) {
			return "redirect:admin-product";			
		}else {
			return "common/500error";
		}
	}

	@Override
	public Product findById(Long id) {
		ResponseEntity<Product> responseEntity = restTemplate.getForEntity("/product/"+id, Product.class, id);
		
		if(responseEntity.getStatusCode()==HttpStatus.INTERNAL_SERVER_ERROR) {
			return null;
		} else if(responseEntity.getStatusCode()==HttpStatus.OK)  {
			return responseEntity.getBody();
		} else {
			return null;
		}
	}

	@Override
	public List<Product> findRelatedProduct(Long categoryId) {
		ResponseEntity<List<Product>> responseEntity = restTemplate.exchange("/product/relate?categoryId="+categoryId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});;
		return responseEntity.getBody();
	}

	@Override
	public Map<String, Object> findByCategoryId(Long categoryId, Integer page) {
		String url = (page==0) ? ("/product/?categoryId="+categoryId) : ("/product/?categoryId="+categoryId+"&page="+page);
		ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});
		return responseEntity.getBody();
	}
	
	@Override
	public Map<String, Object> findAll(Integer page) {
		String url = (page==0) ? "/product/page" : ("/product/page?page="+page);
		ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {});
		return responseEntity.getBody();
	}
	
	@Override
	public List<Product> findAllHome() {
		ResponseEntity<List<Product>> responseEntity = restTemplate.exchange("/product/limit", HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
		return responseEntity.getBody();
	}

	@Override
	public String addToCart(Long productId, HttpSession session) {
		Users users = userService.findByEmail((String) session.getAttribute("userMail"), session);
		Cart cart = Cart.builder()
						.productId(productId)
						.userId(users.getId())
						.quantity(1)
						.price(findById(productId).getPrice())
						.build();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Cart> httpEntity = new HttpEntity<Cart>(cart, headers);
		ResponseEntity<Cart> responseEntity;
		try {
			responseEntity = restTemplate.postForEntity("/cart", httpEntity, Cart.class);
		} catch (Exception e) {
			return "redirect:/login";
		}
		if(responseEntity.getStatusCode()==HttpStatus.FORBIDDEN) {
			return "redirect:/login";
		}else if(responseEntity.getStatusCode()==HttpStatus.OK) {
			return "redirect:/cart";
		}
		return "redirect:product/detail/"+productId;
	}
	
	@Override
	public List<Cart> getCartByUserId(Long userId, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Cart>> httpEntity = new HttpEntity<List<Cart>>(headers);
		ResponseEntity<List<Cart>> responseEntity;
		try {
			responseEntity = restTemplate.exchange("/cart/"+userId, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Cart>>() {});	
		} catch (Exception e) {
			return null;
		}
		return responseEntity.getBody();
	}
	
	@Override
	public List<Cart> getCartByUserMail(String userMail, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Cart>> httpEntity = new HttpEntity<List<Cart>>(headers);
		ResponseEntity<List<Cart>> responseEntity;
		try {
			responseEntity = restTemplate.exchange("/cart/"+userMail, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Cart>>() {});	
		} catch (Exception e) {
			return null;
		}
		return responseEntity.getBody();
	}
	
	@Override
	public String deleteItem(Long productId, Long userId, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
		restTemplate.exchange(String.format("/cart?productId=%d&userId=%d", productId, userId), HttpMethod.DELETE, httpEntity, Object.class);
		
		return "redirect:/cart";
	}
	
	@Override
	public String updateQuantityItem(Long productId, Long userId, Integer quantity) {
		Cart cart = Cart.builder()
				.productId(productId)
				.userId(userId)
				.quantity(quantity)
				.build();
		restTemplate.put("/cart", cart);
		
		return "redirect:/cart";
	}
	
	@Override
	public Long getAmount(List<Cart> carts) {
		Long amount = 0L;
		for (Cart cart : carts) {
			amount = (long) (amount + (cart.getQuantity()*cart.getPrice()));
		}
		return amount;
	}

	@Override
	public List<Product> findByKeyword(String keyword, Long categoryId, HttpSession session) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", (String) session.getAttribute("token"));
		HttpEntity<List<Product>> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<List<Product>> responseEntity = restTemplate.exchange(String.format("/product/search?keyword=%s&categoryId=%d", keyword, categoryId), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Product>>() {});
		
		return responseEntity.getBody();
	}
}
