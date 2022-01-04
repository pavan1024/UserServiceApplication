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
		return userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
	}

	public UserDto addUser(UserDto userDto) throws UserAlreadyExistsException {
		UserDto retrievedUserDto = null;
		User user = mapper.map(userDto, User.class);
		Optional<User> retrievedUser = userRepository.findById(userDto.getUsername());
		if (!retrievedUser.isPresent()) {
			userRepository.save(user);
			retrievedUserDto = mapper.map(user, UserDto.class);
		} else {
			throw new UserAlreadyExistsException("User Already Exists");
		}
		return retrievedUserDto;
	}

	public String deleteUser(String username) throws UserNotFoundException {
		User user = userRepository.findById(username).orElseThrow(() -> new UserNotFoundException(userNotFound));
		userRepository.delete(user);
		String status = "User Deleted Successfully";
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
