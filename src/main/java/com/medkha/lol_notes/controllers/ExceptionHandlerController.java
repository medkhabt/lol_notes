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

import com.medkha.lol_notes.exceptions.IncorrectReturnSizeException;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.util.ErrorMessage;
import com.medkha.lol_notes.util.ErrorMessageWithParams;
import com.medkha.lol_notes.util.FieldErrorMessage;

@ControllerAdvice
public class ExceptionHandlerController {

	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	List<FieldErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) { 
		List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
		return fieldErrors.stream()
				.map(fieldError ->
						new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage())
				)
				.collect(Collectors.toList());
	}
	
	@ResponseBody 
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(NoElementFoundException.class)
	ErrorMessage noElementFoundExceptionExceptionHandler(NoElementFoundException err) { 
		return new ErrorMessage("403", err.getMessage());
	}
	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	ErrorMessage illegaleArgumenetExceptionExceptionHandler(IllegalArgumentException err) { 
		return new ErrorMessage("400", err.getMessage());
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IncorrectReturnSizeException.class)
	ErrorMessageWithParams incorrectReturnSizeExceptionHandler(IncorrectReturnSizeException err) {
		ErrorMessageWithParams errorMessageWithParams = new ErrorMessageWithParams("500", err.getMessage());
		errorMessageWithParams.addParam("expectedSize", String.valueOf(err.getExpectedSize()));
		errorMessageWithParams.addParam("actualSize", String.valueOf(err.getActualSize()));
		return errorMessageWithParams;
	}


}
