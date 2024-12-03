package com.SchoolBack.Service;

import com.SchoolBack.Config.JwtService;
import com.SchoolBack.DTO.loginUser;
import com.SchoolBack.DTO.TokenDTO;
import com.SchoolBack.Repository.UserRepository;
import com.SchoolBack.Entity.User;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Exception.UserNotFoundException;
import com.SchoolBack.Exception.UserServiceBusinessException;
import com.SchoolBack.DTO.registerUser;
import com.SchoolBack.Util.ValueMapper;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final StudentService studentService;
	private final TeacherService teacherService;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	private final ValueMapper mapper;

	public TokenDTO authenticate(loginUser request) {
		User userResult;
		try {
			log.info("UserService:authenticate execution started.");
			Optional<User> us = userRepository.findUserByEmail(request.getEmail());
			if (us.isEmpty()) {
				throw new UserNotFoundException("User not found");
			}
			authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
			request.getEmail(),
			request.getPassword())
			);
			userResult = us.get();
			log.debug("UserService:authenticate user found {}", userResult.getEmail());
		} catch (Exception e) {
			log.error("Exception occurred during authentication , Exception message {}", e.getMessage());
			throw new UserServiceBusinessException("Exception occurred during authentication");
		}
		log.info("UserService:authenticate execution ended.");
		var jwtToken = jwtService.generateToken(userResult);
		return TokenDTO.builder()
		.token(jwtToken)
		.build();
	}

	public void registerUsers(List<registerUser> request) {
		try {
			log.info("UserService:registerUsers execution started.");
			request.parallelStream().forEach(register -> {
				if (register.getTypeUser().equalsIgnoreCase("student")) {
					this.register(register);
				} else if (register.getTypeUser().equalsIgnoreCase("teacher")) {
					this.registerTeacher(register);
				}
			});
		} catch (Exception e) {
			log.error("Exception occurred while persisting a list of users to database , Exception message {}", e.getMessage());
			throw new UserServiceBusinessException("Exception occurred while create a new User");
		}
		log.info("UserService:registerUsers execution ended.");
	}

	public TokenDTO register(registerUser request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			Student student = mapper.convertStudentDTOToStudent(request);
			log.debug("UserService:registerNewUser request parameters {}", mapper.jsonAsString(student));
			Student st = studentService.save(student);
			userResult = st.getUser();
			log.debug("UserService:registerNewStudent received response from Database {}", mapper.jsonAsString(st));
		} catch (Exception e) {
			log.error("Exception occurred while persisting User to database , Exception message {}", e.getMessage());
			throw new UserServiceBusinessException("Exception occurred while create a new User");
		}
		log.info("UserService:registerNewUser execution ended.");
		var jwtToken = jwtService.generateToken(userResult);
		return TokenDTO.builder()
		.token(jwtToken)
		.build();
	}

	public TokenDTO registerTeacher(registerUser request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			Teacher teacher = mapper.convertTeacherDTOToTeacher(request);
			log.debug("UserService:registerNewUser request parameters {}", mapper.jsonAsString(teacher));
			Teacher th = teacherService.save(teacher);
			userResult = th.getUser();
			log.debug("UserService:registerNewTeacher received response from Database {}", mapper.jsonAsString(th));
		} catch (Exception e) {
			log.error("Exception occurred while persisting User to database , Exception message {}", e.getMessage());
			throw new UserServiceBusinessException("Exception occurred while create a new User");
		}
		log.info("UserService:registerNewUser execution ended.");
		var jwtToken = jwtService.generateToken(userResult);
		return TokenDTO.builder()
		.token(jwtToken)
		.build();
	}

	public TokenDTO registerAdmin(registerUser request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			User user = mapper.convertRegisterDTOToAdmin(request);
			log.debug("UserService:registerNewUser request parameters {}", mapper.jsonAsString(user));
			User us = userRepository.save(user);
			userResult = us;
			log.debug("UserService:registerNewAdminr received response from Database {}", mapper.jsonAsString(us));
		} catch (Exception e) {
			log.error("Exception occurred while persisting User to database , Exception message {}", e.getMessage());
			throw new UserServiceBusinessException("Exception occurred while create a new User");
		}
		log.info("UserService:registerNewUser execution ended.");
		var jwtToken = jwtService.generateToken(userResult);
		return TokenDTO.builder()
		.token(jwtToken)
		.build();
	}
}
