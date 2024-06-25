package com.security.SpringSecurity.Exception;

public class StudentNotFoundException extends RuntimeException{
	
	public StudentNotFoundException(String message){
		super(message);
	}
}
