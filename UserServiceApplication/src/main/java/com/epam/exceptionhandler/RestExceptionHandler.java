package com.epam.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.epam.exception.NoUsersException;
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
	public Map<String, String> handleUserNotFoundException(UserNotFoundException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(userService, users);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return response;
	}

	@ExceptionHandler(value = UserAlreadyExistsException.class)
	public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(userService, users);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.CONFLICT.name());
		return response;
	}

	@ExceptionHandler(value = NoUsersException.class)
	public Map<String, String> handleNoUsersException(NoUsersException ex) {
		Map<String, String> response = new HashMap<>();
		response.put(userService, users);
		response.put(timestamp, new Date().toString());
		response.put(error, ex.getMessage());
		response.put(status, HttpStatus.NOT_FOUND.name());
		return response;
	}
}
