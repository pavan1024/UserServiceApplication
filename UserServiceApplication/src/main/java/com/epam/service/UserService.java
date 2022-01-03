package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exception.NoUsersException;
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

	public List<User> fetchAllUsers() throws NoUsersException {
		List<User> books = (List<User>) userRepository.findAll();
		if (books.isEmpty()) {
			throw new NoUsersException("No Users");
		}
		return books;
	}

	public User getUser(String username) throws UserNotFoundException {
		User retrivedUser = null;
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			retrivedUser = user.get();
		} else {
			throw new UserNotFoundException(userNotFound);
		}
		return retrivedUser;
	}

	public UserDto addUser(UserDto userDto) throws UserAlreadyExistsException {
		UserDto userDto1 = null;
		User user = mapper.map(userDto, User.class);
		Optional<User> user1= userRepository.findById(userDto.getUsername());
		if(!user1.isPresent()) {
			userRepository.save(user);
			userDto1 = mapper.map(user, UserDto.class);
		}
		else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		return userDto1;
	}

	public String deleteUser(String username) throws UserNotFoundException {
		String status = "";
		User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
		if (user != null) {
			userRepository.delete(user);
			status = "User Deleted Successfully";
		}
		return status;
	}

	public UserDto updateUser(String username, UserDto userDto) {		
		User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		userRepository.save(user);
		return mapper.map(user, UserDto.class);
		
	}
}
