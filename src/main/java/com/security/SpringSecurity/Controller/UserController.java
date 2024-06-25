package com.security.SpringSecurity.Controller;

import com.security.SpringSecurity.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.SpringSecurity.Model.APIResponse;
import com.security.SpringSecurity.Model.AuthUserDTO;
import com.security.SpringSecurity.Model.TokenDTO;
import com.security.SpringSecurity.Model.studentDTO;
import com.security.SpringSecurity.Model.teacherDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<APIResponse> login(@Valid @RequestBody AuthUserDTO loginDTO) {
		
		TokenDTO token = userService.authenticate(loginDTO);
		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<APIResponse> register(@Valid @RequestBody studentDTO userDTO) {
		TokenDTO token = userService.register(userDTO);
		
		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	@PostMapping("/register/teacher")
	public ResponseEntity<APIResponse> registerTeacher(@Valid @RequestBody teacherDTO userDTO) {
		TokenDTO token = userService.registerTeacher(userDTO);
		
		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@PostMapping("/register/admin")
	public ResponseEntity<APIResponse> registerAdmin(@Valid @RequestBody studentDTO userDTO) {
		TokenDTO token = userService.registerAdmin(userDTO);
		
		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
