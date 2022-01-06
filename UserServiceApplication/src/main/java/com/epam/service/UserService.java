package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;
import com.epam.repo.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper mapper;

	String userNotFound = "User Not Found";

	public List<User> fetchAllUsers() {
		return (List<User>) userRepository.findAll();
	}

	public User getUser(String username) throws UserNotFoundException {
		return userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
	}

	public UserDto addUser(UserDto userDto) throws UserAlreadyExistsException {
		Optional<User> retrievedUser = userRepository.findById(userDto.getUsername());
		if (retrievedUser.isPresent()) {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		User user = mapper.map(userDto, User.class);
		userRepository.save(user);
		return userDto;

	}

	public String deleteUser(String username) throws UserNotFoundException {
		User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
		userRepository.delete(user);
		return "User Deleted Successfully";
	}

	public UserDto updateUser(String username, UserDto userDto) {
		User retrievedUser = userRepository.findById(username)
				.orElseThrow(() -> new UserNotFoundException(userNotFound));
		userDto.setUsername(username);
		mapper.map(userDto, User.class);
		userRepository.save(retrievedUser);
		return userDto;

	}
}
