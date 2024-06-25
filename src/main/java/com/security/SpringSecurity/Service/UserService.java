package com.security.SpringSecurity.Service;

import com.security.SpringSecurity.Config.JwtService;
import com.security.SpringSecurity.Entity.Student;
import com.security.SpringSecurity.Entity.Teacher;
import com.security.SpringSecurity.Entity.User;
import com.security.SpringSecurity.Exception.UserNotFoundException;
import com.security.SpringSecurity.Exception.UserServiceBusinessException;
import com.security.SpringSecurity.Model.AuthUserDTO;
import com.security.SpringSecurity.Model.TokenDTO;
import com.security.SpringSecurity.Model.studentDTO;
import com.security.SpringSecurity.Model.teacherDTO;
import com.security.SpringSecurity.Repository.StudentRepository;
import com.security.SpringSecurity.Repository.TeacherRepository;
import com.security.SpringSecurity.Repository.UserRepository;
import com.security.SpringSecurity.Util.ValueMapper;
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
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public TokenDTO authenticate(AuthUserDTO request) {
		User userResult;
		try {
			log.info("UserService:authenticate execution started.");
			authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
			request.getEmail(),
			request.getPassword())
			);

			Optional<User> us = userRepository.findUserByEmail(request.getEmail());
			if (us.isEmpty()) {
				throw new UserNotFoundException("User not found");
			}
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

	public TokenDTO register(studentDTO request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			Student student = ValueMapper.convertStudentDTOToStudent(request);
			log.debug("UserService:registerNewUser request parameters {}", ValueMapper.jsonAsString(student));
			Student st = studentRepository.save(student);
			userResult = st.getUser();
			log.debug("UserService:registerNewStudent received response from Database {}", ValueMapper.jsonAsString(st));
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
	
	public TokenDTO registerTeacher(teacherDTO request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			Teacher teacher = ValueMapper.convertTeacherDTOToTeacher(request);
			log.debug("UserService:registerNewUser request parameters {}", ValueMapper.jsonAsString(teacher));
			Teacher th = teacherRepository.save(teacher);
			userResult = th.getUser();
			log.debug("UserService:registerNewTeacher received response from Database {}", ValueMapper.jsonAsString(th));
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
	
	public TokenDTO registerAdmin(studentDTO request) {
		User userResult;
		try {
			log.info("UserService:register execution started.");
			request.setPassword(passwordEncoder.encode(request.getPassword()));
			User user = ValueMapper.convertTeacherDTOToTeacher(request);
			log.debug("UserService:registerNewUser request parameters {}", ValueMapper.jsonAsString(user));
			User us = userRepository.save(user);
			userResult = us;
			log.debug("UserService:registerNewAdminr received response from Database {}", ValueMapper.jsonAsString(us));
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
