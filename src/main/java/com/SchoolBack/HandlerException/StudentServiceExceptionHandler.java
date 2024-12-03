package com.SchoolBack.HandlerException;

import com.SchoolBack.Exception.StudentNotFoundException;
import com.SchoolBack.Exception.StudentServiceBusinessException;
import com.SchoolBack.DTO.APIResponse;
import com.SchoolBack.DTO.ErrorDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentServiceExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception) {
		APIResponse<?> serviceResponse = new APIResponse<>();
		List<ErrorDTO> errors = new ArrayList<>();

		exception.getBindingResult().getFieldErrors()
		.forEach(error -> {
			ErrorDTO errorDTO = new ErrorDTO(error.getField(), error.getDefaultMessage());
			errors.add(errorDTO);
		});
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(errors);
		return serviceResponse;
	}

	@ExceptionHandler(StudentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public APIResponse<?> handleStudentNotFoundException(StudentNotFoundException exception) {
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}

	@ExceptionHandler(StudentServiceBusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public APIResponse<?> handleStudentServiceBusnessException(StudentServiceBusinessException exception) {
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}
}
