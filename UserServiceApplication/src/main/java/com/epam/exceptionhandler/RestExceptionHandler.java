package com.epam.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.UserAlreadyExistsException;
import com.epam.exception.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {
	String userService = "userService";
	String users = "users";
	String timestamp = "timestamp";
	String error = "error";
	String status = "status";

	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(userService, users);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(userService, users);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
