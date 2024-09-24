package com.SchoolBack.Controller;

import com.SchoolBack.Model.teacherUpdateDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Model.APIResponse;
import com.SchoolBack.Service.TeacherService;
import com.SchoolBack.Util.ValueMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.SchoolBack.Model.teacherResponseDTO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/teacher")
@Slf4j
public class TeacherController {

	private final TeacherService teacherService;
	private final ValueMapper mapper;

//	@PostMapping
//	public ResponseEntity<APIResponse> saveTeacher(@Valid @RequestBody teacherUpdateDTO teacher) {
//
//		log.info("TeacherController::saveTeacher request body {}", ValueMapper.jsonAsString(teacher));
//		Teacher th = teacherService.save(teacher);
//
//		APIResponse<Teacher> responseDTO = APIResponse
//		.<Teacher>builder()
//		.status(HttpStatus.OK.getReasonPhrase())
//		.results(th)
//		.build();
//
//		log.info("TeacherController::saveTeacher response {}", ValueMapper.jsonAsString(responseDTO));
//		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
//	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getTeacher(@PathVariable("id") Long id) {
		log.info("TeacherController::getTeacher by id  {}", id);
		Teacher th = teacherService.findById(id);
		th.getClasses();
		
		teacherResponseDTO teacher = mapper.convertTeacherToTeacherDTO(th);
		
		APIResponse<teacherResponseDTO> responseDTO = APIResponse
		.<teacherResponseDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(teacher)
		.build();

		log.info("TeacherController::getTeacher by id {} response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<APIResponse<List<teacherResponseDTO>>> getAllTeacher(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection,
		@RequestParam(defaultValue = "") String filter) {

		log.info("TeacherController::getAllTeacher with page {} size {} sortBy {} sortDirection {} filter {}", page, size, sortBy, sortDirection,filter);
		Page<Teacher> teachers = teacherService.findAll(page, size, sortBy, sortDirection,filter);

		Page<teacherResponseDTO> thDTOResponse = teachers.map(teacher -> mapper.convertTeacherToTeacherDTO(teacher));
		
		APIResponse<List<teacherResponseDTO>> responseDTO = APIResponse
		.<List<teacherResponseDTO>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(thDTOResponse.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(thDTOResponse.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		log.info("TeacherController::getAllTeacher response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateTeacher(@PathVariable("id") Long id, @RequestBody teacherUpdateDTO updatedTeacher) {
		log.info("TeacherController::updateTeacher with id {} request body {} ", id, mapper.jsonAsString(updatedTeacher));
		Teacher th = teacherService.update(id, updatedTeacher);

		APIResponse<Teacher> responseDTO = APIResponse
		.<Teacher>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(th)
		.build();

		log.info("TeacherController::updateTeacher response {}", mapper.jsonAsString(responseDTO));
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

		log.info("TeacherController::deleteTeacher by id {}  response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

}
