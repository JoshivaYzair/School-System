package com.SchoolBack.Controller;

import com.SchoolBack.Model.registerUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Model.APIResponse;
import com.SchoolBack.Model.studentUpdateDTO;
import com.SchoolBack.Model.studentResponseDTO;
import com.SchoolBack.Service.StudentService;
import com.SchoolBack.Util.ValueMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/student")
@Slf4j
public class StudentController {

	private final StudentService studentService;
	private final ValueMapper mapper;

//	@PostMapping
//	public ResponseEntity<APIResponse> saveStudent(@RequestBody @Valid registerUser student) {
//		log.info("StudentController::createNewStudent request body {}", ValueMapper.jsonAsString(student));
//		Student st = studentService.save(student);
//
//		APIResponse<Student> responseDTO = APIResponse
//		.<Student>builder()
//		.status(HttpStatus.OK.getReasonPhrase())
//		.results(st)
//		.build();
//
//		log.info("StudentController::createNewStudent response {}", ValueMapper.jsonAsString(responseDTO));
//		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
//	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getStudent(@PathVariable("id") Long id) {
		log.info("StudentController::getStudent by id  {}", id);
		
		Student st = studentService.findStudentById(id);
		
		studentResponseDTO stDTO = mapper.convertStudentToStudentDTO(st,true);
		
		APIResponse<studentResponseDTO> responseDTO = APIResponse
		.<studentResponseDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(stDTO)
		.build();

		log.info("StudentController::getStudent by id {} response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<APIResponse<List<studentResponseDTO>>> getAllStudent(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection,
		@RequestParam(defaultValue = "") String filter,
		@RequestParam(required = false) List<String> filters) {

		log.info("StudentController::getAllStudent with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Student> students = studentService.findAll(page, size, sortBy, sortDirection,filter, filters);
		
		Page<studentResponseDTO> stDTOResponse = students.map(student -> mapper.convertStudentToStudentDTO(student, false));
			
		APIResponse<List<studentResponseDTO>> responseDTO = APIResponse
		.<List<studentResponseDTO>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(stDTOResponse.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(stDTOResponse.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		log.info("StudentController::getAllStudent response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody studentUpdateDTO updatedStudent) {
		log.info("StudentController::updateStudent with id {} request body {} ", id, mapper.jsonAsString(updatedStudent));
		Student st = studentService.update(id, updatedStudent);

		APIResponse<Student> responseDTO = APIResponse
		.<Student>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(st)
		.build();

		log.info("StudentController::updateStudent response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<APIResponse> deleteStudent(@PathVariable("id") Long id) {
		log.info("StudentController::deleteStudent by id  {}", id);
		studentService.deleteById(id);

		APIResponse<Student> responseDTO = APIResponse
		.<Student>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();

		log.info("StudentController::deleteStudent by id {}  response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NO_CONTENT);
	}

}
