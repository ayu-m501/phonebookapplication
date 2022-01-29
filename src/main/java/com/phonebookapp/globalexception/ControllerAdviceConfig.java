package com.phonebookapp.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.phonebookapp.exception.ContactError;
import com.phonebookapp.exception.RecordNotFoundException;

@RestController
@ControllerAdvice
public class ControllerAdviceConfig {

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<ContactError> handle() {
        ContactError error  = new ContactError(400, "given id not available");
		return new ResponseEntity<ContactError>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value =NullPointerException.class)
	public ResponseEntity<ContactError> handleEx() {
		ContactError error = new ContactError(400, "name,email,contactNo. can't give empty");
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}

}
