package com.springboot.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceRestExceptionHandler {

	//Exception handler method
	@ExceptionHandler
	public ResponseEntity<ResourceErrorResponse> handleException(ResourceNotFoundException ex){
		ResourceErrorResponse error = new ResourceErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ResourceErrorResponse>(error, HttpStatus.NOT_FOUND); 
	}
	
	//Exception handler method to catch any type of exception
	@ExceptionHandler
	public ResponseEntity<ResourceErrorResponse> handleException(Exception ex){
		ResourceErrorResponse error = new ResourceErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage("Invalid request is passed to get the data");
		error.setTimeStamp(System.currentTimeMillis());
		return new ResponseEntity<ResourceErrorResponse>(error, HttpStatus.BAD_REQUEST); 
	}
}
