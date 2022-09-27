package com.example.servercomputer.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.servercomputer.dto.UserDTO;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private UserRepository userRepository;
	private ModelMapper modelMapper;
	private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<UserDTO> findAll() {
		return userRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO findOneById(Long id) {
		return toDTO(userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not Found User")));
	}

	@Override
	public UserDTO save(UserDTO user) {
		if (user.getId() != null) {
			User oldUser = userRepository.findById(user.getId())
					.orElseThrow(() -> new IllegalStateException("Not Found User"));
			oldUser.setFirstName(user.getFirstName());
			oldUser.setLastName(user.getLastName());
			oldUser.setGender(user.getGender());
			oldUser.setAddress(user.getAddress());
			oldUser.setBirthday(user.getBirthday());
			oldUser.setPhoneNumber(user.getPhoneNumber());
			return toDTO(userRepository.save(oldUser));
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return toDTO(userRepository.save(toEntity(user)));
	}

	@Override
	public boolean delete(Long id) {
		userRepository.findById(id).orElseThrow(() -> new IllegalStateException("Not Found User"));
		userRepository.deleteById(id);
		return true;
	}

	@Override
	public UserDTO login(UserDTO user) {
		return toDTO(userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()));
	}
	
	@Override
	public UserDTO changePassword(UserDTO user) {
		User oldUser = userRepository.findByEmail(user.getEmail()).orElseThrow(()-> new IllegalStateException("Not Found User"));
		oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		return toDTO(userRepository.save(oldUser));
	}
	
	@Override
	public UserDTO findByEmail(String userMail) {
		User user = userRepository.findByEmail(userMail).orElse(null);
		return toDTO(user);
	}
	
	@Override
	public void createResetToken(String token, String email) {
		User user = userRepository.findByEmail(email).orElse(null);
		if(user!=null) {
			user.setResetPassToken(token);
			userRepository.save(user);
		} else {
			throw new IllegalStateException("Not found user with email " + email);
		}
	}
	
	@Override
	public UserDTO findByResetToken(String token) {
		return toDTO(userRepository.findByResetPassToken(token));
	}
	
	@Override
	public void updateNewPassword(String userMail, String newPassword) {
		System.out.println(userMail);
		User user = userRepository.findByEmail(userMail).orElse(null);
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setResetPassToken(null);
		userRepository.save(user);
	}

	private UserDTO toDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}

	private User toEntity(UserDTO userDTO) {
		return modelMapper.map(userDTO, User.class);
	}
}
