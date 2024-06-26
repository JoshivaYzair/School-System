package com.SchoolBack.Exception;

public class CourseNotFoundException extends RuntimeException{

	public CourseNotFoundException(String message) {
		super(message);
	}
}
