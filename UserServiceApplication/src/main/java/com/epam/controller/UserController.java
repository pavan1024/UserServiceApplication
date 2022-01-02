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
import com.epam.exception.NoUsersException;
import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;
import com.epam.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() throws NoUsersException {
		return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
	}
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) throws UserNotFoundException {
		User user = null;
		HttpStatus statusCode = null;
		if(userService.getUser(username)!=null) {
			user = userService.getUser(username);
			statusCode = HttpStatus.OK;
		}
		return new ResponseEntity<>(user,statusCode);
	}

	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody UserDto userDto) throws UserAlreadyExistsException{
		String status = "";
		HttpStatus statusCode = null;
		if (userService.addUser(userDto)) {
			status = "User Added Successfully";
			statusCode = HttpStatus.ACCEPTED;
		} else {
			status = "User Addition Unsuccessful";
			statusCode = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status, statusCode);
	}
	
	@DeleteMapping("/{username}")
	public ResponseEntity<String> deleteUser(@PathVariable String username) throws UserNotFoundException{
		String status = "";
		HttpStatus statusCode = null;
		if (userService.deleteUser(username)) {
			status = "User Deleted Successfully";
			statusCode = HttpStatus.ACCEPTED;
		} else {
			status = "User Deletion Unsuccessful";
			statusCode = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status, statusCode);
	}
	
	@PutMapping("/{username}")
	public ResponseEntity<String> updateUser(@PathVariable String username,@RequestBody UserDto userDto) throws UserNotFoundException{
		String status = "";
		HttpStatus statusCode = null;
		if (userService.updateUser(username,userDto)) {
			status = "User Updated Successfully";
			statusCode = HttpStatus.ACCEPTED;
		} else {
			status = "User Updation Unsuccessful";
			statusCode = HttpStatus.NOT_FOUND;
		}
		return new ResponseEntity<>(status, statusCode);
	}
}
