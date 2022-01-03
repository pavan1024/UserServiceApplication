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
			throw new UserNotFoundException("User Not Found");
		}
		return retrivedUser;
	}

	public boolean addUser(UserDto userDto) throws UserAlreadyExistsException {
		boolean status = false;
		Optional<User> optionalUser = userRepository.findById(userDto.getUsername());
		User user = mapper.map(userDto, User.class);
		if (!optionalUser.isPresent()) {
			userRepository.save(user);
			status = true;
		} else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		return status;
	}

	public boolean deleteUser(String username) throws UserNotFoundException {
		boolean status = false;
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			userRepository.delete(user.get());
			status = true;
		} else {
			throw new UserNotFoundException("User Not Found");
		}
		return status;
	}

	public boolean updateUser(String username, UserDto userDto) {
		boolean status = false;
		Optional<User> user = userRepository.findById(username);
		if (user.isPresent()) {
			user.get().setName(userDto.getName());
			user.get().setEmail(userDto.getEmail());
			userRepository.save(user.get());
			status = true;
		} else {
			throw new UserNotFoundException("User Not Found");
		}
		return status;
	}
}
