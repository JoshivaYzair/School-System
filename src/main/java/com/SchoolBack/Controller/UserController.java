package com.SchoolBack.Controller;

import com.SchoolBack.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SchoolBack.Model.APIResponse;
import com.SchoolBack.Model.loginUser;
import com.SchoolBack.Model.TokenDTO;
import com.SchoolBack.Model.registerUser;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<APIResponse> login(@Valid @RequestBody loginUser loginDTO) {

		TokenDTO token = userService.authenticate(loginDTO);
		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<APIResponse> register(@Valid @RequestBody registerUser register) {
		APIResponse<TokenDTO> responseDTO = null;

		if (register.getTypeUser().equalsIgnoreCase("student")) {
			TokenDTO token = userService.register(register);

			APIResponse<TokenDTO> response = APIResponse
			.<TokenDTO>builder()
			.status(HttpStatus.OK.getReasonPhrase())
			.results(token)
			.build();

			responseDTO = response;
		} else if (register.getTypeUser().equalsIgnoreCase("teacher")) {
			TokenDTO token = userService.registerTeacher(register);

			APIResponse<TokenDTO> response = APIResponse
			.<TokenDTO>builder()
			.status(HttpStatus.OK.getReasonPhrase())
			.results(token)
			.build();

			responseDTO = response;
		}

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PostMapping("/register/users")
	public ResponseEntity<APIResponse> registerUsers(@Valid @RequestBody List<registerUser> register) {
		
		userService.registerUsers(register);
		
		APIResponse<String> response = APIResponse
		.<String>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	@PostMapping("/register/teacher")
//	public ResponseEntity<APIResponse> registerTeacher(@Valid @RequestBody teacherDTO userDTO) {
//		TokenDTO token = userService.registerTeacher(userDTO);
//
//		APIResponse<TokenDTO> responseDTO = APIResponse
//		.<TokenDTO>builder()
//		.status(HttpStatus.OK.getReasonPhrase())
//		.results(token)
//		.build();
//
//		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//	}
	@PostMapping("/register/admin")
	public ResponseEntity<APIResponse> registerAdmin(@Valid @RequestBody registerUser userDTO) {
		TokenDTO token = userService.registerAdmin(userDTO);

		APIResponse<TokenDTO> responseDTO = APIResponse
		.<TokenDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(token)
		.build();

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
