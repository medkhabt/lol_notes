package com.medkha.lol_notes.exceptions;

public class NoElementFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5227428115754334614L;
	
	public NoElementFoundException(String errorMessage, Throwable err) { 
		super(errorMessage, err);
		 
		
		
	}

	public NoElementFoundException(String errorMessage) {
		super(errorMessage); 
	}

}
