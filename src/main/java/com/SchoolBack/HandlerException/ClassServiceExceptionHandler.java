
package com.SchoolBack.HandlerException;

import com.SchoolBack.Model.APIResponse;
import com.SchoolBack.Model.ErrorDTO;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.SchoolBack.Exception.ClassNotFoundException;
import com.SchoolBack.Exception.ClassServiceBuisinessException;

@RestControllerAdvice
public class ClassServiceExceptionHandler {
	
	@ExceptionHandler(ClassNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public APIResponse<?> handleStudentNotFoundException(ClassNotFoundException exception) {
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}

	@ExceptionHandler(ClassServiceBuisinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public APIResponse<?> handleStudentServiceBusnessException(ClassServiceBuisinessException exception) {
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}
}
