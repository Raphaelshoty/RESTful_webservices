package com.webServices.rest.restFulWebServices.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.webServices.rest.restFulWebServices.user.UserNotFoundException;


@ControllerAdvice // this annotation tells that this controller will be consumed across the requests on project, this annotation has precedence over the other response @ResponseStatus on exception classes
@RestController // this component will provide response to errors so it need to be an controller
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class) // generic exception 
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {		
		ExceptionResponse exception =new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));		
		return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@ExceptionHandler(UserNotFoundException.class) // specific exception on userNotFoundException
	public final ResponseEntity<Object> handleAllUserNotFoundException(Exception ex, WebRequest request) {		
		ExceptionResponse exception =new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));		
		return new ResponseEntity(exception, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(UsersNotFoundException.class) // specific exception on usersNotFoundException
	public final ResponseEntity<Object> handleAllUsersNotFoundException(Exception ex, WebRequest request) {		
		ExceptionResponse exception =new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));		
		return new ResponseEntity(exception, HttpStatus.NOT_FOUND);		
	}
	
	@ExceptionHandler(PostNotFoundException.class) // specific exception on PostNotFoundException
	public final ResponseEntity<Object> handleAllPostNotFoundException(Exception ex, WebRequest request) {		
		ExceptionResponse exception =new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));		
		return new ResponseEntity(exception, HttpStatus.NOT_FOUND);		
	}
			
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exception =new ExceptionResponse(new Date(), "Validation Failed", ex.getBindingResult().toString()); //  ex.getBindingResult().toString() get the results of what went wrong 
		return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);	
	}

}
