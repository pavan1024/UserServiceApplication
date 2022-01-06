package com.epam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dto.UserDto;
import com.epam.entity.User;
import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;
import com.epam.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
	}

	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) throws UserNotFoundException {
		return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
		return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username) throws UserNotFoundException {
		return new ResponseEntity<>(userService.deleteUser(username), HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{username}")
	public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserDto userDto)
			throws UserNotFoundException {
		return new ResponseEntity<>(userService.updateUser(username, userDto), HttpStatus.OK);
	}

}
