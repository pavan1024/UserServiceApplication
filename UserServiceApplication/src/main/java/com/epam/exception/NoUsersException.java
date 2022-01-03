package com.epam.exception;

public class NoUsersException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoUsersException(String message){
		super(message);
	}

}
