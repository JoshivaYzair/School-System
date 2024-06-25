package com.security.SpringSecurity.Controller;

import com.security.SpringSecurity.Model.teacherDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.SpringSecurity.Entity.Teacher;
import com.security.SpringSecurity.Model.APIResponse;
import com.security.SpringSecurity.Model.PagedResponseDTO;
import com.security.SpringSecurity.Service.TeacherService;
import com.security.SpringSecurity.Util.ValueMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teacher")
@Slf4j
public class TeacherController {

	private final TeacherService teacherService;

	@PostMapping
	public ResponseEntity<APIResponse> saveTeacher(@Valid @RequestBody teacherDTO teacher) {

		log.info("TeacherController::saveTeacher request body {}", ValueMapper.jsonAsString(teacher));
		Teacher th = teacherService.save(teacher);

		APIResponse<Teacher> responseDTO = APIResponse
		.<Teacher>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(th)
		.build();

		log.info("TeacherController::saveTeacher response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getTeacher(@PathVariable("id") Long id) {
		log.info("TeacherController::getTeacher by id  {}", id);
		Teacher th = teacherService.findById(id);

		APIResponse<Teacher> responseDTO = APIResponse
		.<Teacher>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(th)
		.build();

		log.info("TeacherController::getTeacher by id {} response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<APIResponse<PagedResponseDTO<Teacher>>> getAllTeacher(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection) {

		log.info("TeacherController::getAllTeacher with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Teacher> teachers = teacherService.findAll(page, size, sortBy, sortDirection);

		PagedResponseDTO<Teacher> pagedResponseDTO = PagedResponseDTO.<Teacher>builder()
		.content(teachers.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(teachers.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		APIResponse<PagedResponseDTO<Teacher>> responseDTO = APIResponse
		.<PagedResponseDTO<Teacher>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(pagedResponseDTO)
		.build();

		log.info("TeacherController::getAllTeacher response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateTeacher(@PathVariable("id") Long id, @RequestBody teacherDTO updatedTeacher) {
		log.info("TeacherController::updateTeacher with id {} request body {} ", id, ValueMapper.jsonAsString(updatedTeacher));
		Teacher th = teacherService.update(id, updatedTeacher);

		APIResponse<Teacher> responseDTO = APIResponse
		.<Teacher>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(th)
		.build();

		log.info("TeacherController::updateTeacher response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteTeacher(@PathVariable("id") Long id) {
		log.info("TeacherController::deleteTeacher by id  {}", id);
		teacherService.deleteById(id);

		APIResponse<Teacher> responseDTO = APIResponse
		.<Teacher>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();

		log.info("TeacherController::deleteTeacher by id {}  response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

}
