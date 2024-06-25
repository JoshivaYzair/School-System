package com.security.SpringSecurity.Controller;

import com.security.SpringSecurity.Model.studentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.SpringSecurity.Entity.Student;
import com.security.SpringSecurity.Model.APIResponse;
import com.security.SpringSecurity.Model.PagedResponseDTO;
import com.security.SpringSecurity.Service.StudentService;
import com.security.SpringSecurity.Util.ValueMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping
	public ResponseEntity<APIResponse> saveStudent(@RequestBody @Valid studentDTO student) {
		log.info("StudentController::createNewStudent request body {}", ValueMapper.jsonAsString(student));
		Student st = studentService.save(student);

		APIResponse<Student> responseDTO = APIResponse
		.<Student>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(st)
		.build();

		log.info("StudentController::createNewStudent response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getStudent(@PathVariable("id") Long id) {
		log.info("StudentController::getStudent by id  {}", id);
		Student st = studentService.findById(id);

		APIResponse<Student> responseDTO = APIResponse
		.<Student>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(st)
		.build();

		log.info("StudentController::getStudent by id {} response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<APIResponse<PagedResponseDTO<Student>>> getAllStudent(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection) {

		log.info("StudentController::getAllStudent with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Student> students = studentService.findAll(page, size, sortBy, sortDirection);

		PagedResponseDTO<Student> pagedResponseDTO = PagedResponseDTO.<Student>builder()
		.content(students.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(students.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		APIResponse<PagedResponseDTO<Student>> responseDTO = APIResponse
		.<PagedResponseDTO<Student>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(pagedResponseDTO)
		.build();

		log.info("StudentController::getAllStudent response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody studentDTO updatedStudent) {
		log.info("StudentController::updateStudent with id {} request body {} ", id, ValueMapper.jsonAsString(updatedStudent));
		Student st = studentService.update(id, updatedStudent);

		APIResponse<Student> responseDTO = APIResponse
		.<Student>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(st)
		.build();

		log.info("StudentController::updateStudent response {}", ValueMapper.jsonAsString(responseDTO));
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

		log.info("StudentController::deleteStudent by id {}  response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

}
