
package com.SchoolBack.HandlerException;

import com.SchoolBack.Exception.TeacherNotFoundException;
import com.SchoolBack.Exception.TeacherServiceBusinessException;
import com.SchoolBack.DTO.APIResponse;
import com.SchoolBack.DTO.ErrorDTO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TeacherServiceExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public APIResponse<?> handleMethodArgumentException(MethodArgumentNotValidException exception){
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
	
	@ExceptionHandler(TeacherNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public APIResponse<?> handleTeacherNotFoundException(TeacherNotFoundException exception){
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}
	
	@ExceptionHandler(TeacherServiceBusinessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public APIResponse<?> handleTeacherServiceBusinessException(TeacherServiceBusinessException exception){
		APIResponse<?> serviceResponse = new APIResponse<>();
		serviceResponse.setStatus("FAILED");
		serviceResponse.setErrors(Collections.singletonList(new ErrorDTO("", exception.getMessage())));
		return serviceResponse;
	}
}
