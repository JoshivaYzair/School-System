package com.security.SpringSecurity.Controller;

import com.security.SpringSecurity.Service.CourseService;
import lombok.RequiredArgsConstructor;
import com.security.SpringSecurity.Entity.Course;
import com.security.SpringSecurity.Model.APIResponse;
import com.security.SpringSecurity.Model.PagedResponseDTO;
import com.security.SpringSecurity.Model.courseDTO;
import com.security.SpringSecurity.Util.ValueMapper;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
@Slf4j
public class CourseController {
	
	private final CourseService courseService;
	
	@PostMapping
	public ResponseEntity<APIResponse> saveCourse( @Valid @RequestBody courseDTO course) {
		log.info("CourseController::createNewCourse request body {}", ValueMapper.jsonAsString(course));
		Course cs = courseService.save(course);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(cs)
		.build();

		log.info("CourseController::createNewCourse response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getCourse(@PathVariable("id") Long id) {
		log.info("CourseController::getCourse by id  {}", id);
		Course cs = courseService.findById(id);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(cs)
		.build();

		log.info("CourseController::getCourse by id {} response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<APIResponse<PagedResponseDTO<Course>>> getAllCourses(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection) {

		log.info("CourseController::getAllCourses with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Course> students = courseService.findAll(page, size, sortBy, sortDirection);

		PagedResponseDTO<Course> pagedResponseDTO = PagedResponseDTO.<Course>builder()
		.content(students.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(students.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		APIResponse<PagedResponseDTO<Course>> responseDTO = APIResponse
		.<PagedResponseDTO<Course>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(pagedResponseDTO)
		.build();

		log.info("CourseController::getAllCourses response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateCourse(@PathVariable("id") Long id, @RequestBody courseDTO updatedCourse) {
		log.info("CourseController::updateCourse with id {} request body {} ", id, ValueMapper.jsonAsString(updatedCourse));
		Course st = courseService.update(id, updatedCourse);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(st)
		.build();

		log.info("CourseController::updateCourse response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteCourse(@PathVariable("id") Long id) {
		log.info("CourseController::deleteCourse by id  {}", id);
		courseService.deleteById(id);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();

		log.info("CourseController::deleteCourse by id {}  response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}
}
