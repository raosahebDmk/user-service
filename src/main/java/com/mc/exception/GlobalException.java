package com.mc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mc.payload.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFound ex)
	{
		String message = ex.getMessage();
		ApiResponse response = ApiResponse.builder().message(message).success("Not Found data...").status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
	}
	
}
