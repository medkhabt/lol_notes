package com.medkha.lol_notes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.util.ErrorMessage;
import com.medkha.lol_notes.util.FieldErrorMessage;

@ControllerAdvice
public class ExceptionHandlerController {

	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) { 
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		List<FieldErrorMessage> fieldErrorMessages = 
				fieldErrors.stream()
					.map(fieldError ->  
						new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())
					)
					.collect(Collectors.toList()); 
		return fieldErrorMessages; 
	}
	
	@ResponseBody 
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(NoElementFoundException.class)
	ErrorMessage noElementFoundExceptionExceptionHandler(NoElementFoundException err) { 
		return new ErrorMessage("403", err.getMessage());
	}
}
