package com.SchoolBack.Controller;

import com.SchoolBack.Service.CourseService;
import lombok.RequiredArgsConstructor;
import com.SchoolBack.Entity.Course;
import com.SchoolBack.DTO.APIResponse;
import com.SchoolBack.DTO.Request.Course.courseUpdateDTO;
import com.SchoolBack.Util.ValueMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import com.SchoolBack.DTO.courseResponseDTO;
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
	private final ValueMapper mapper;

	@PostMapping
	public ResponseEntity<APIResponse> saveCourse(@Valid @RequestBody courseUpdateDTO course) {
		log.info("CourseController::createNewCourse request body {}", mapper.jsonAsString(course));
		Course cs = courseService.save(course);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.CREATED.getReasonPhrase())
		.results(cs)
		.build();

		log.info("CourseController::createNewCourse response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getCourse(@PathVariable("id") Long id) {
		log.info("CourseController::getCourse by id  {}", id);
		Course cs = courseService.findById(id);

		courseResponseDTO couseDTO = mapper.convertCourseToCourseDTO(cs, true, true);

		APIResponse<courseResponseDTO> responseDTO = APIResponse
		.<courseResponseDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(couseDTO)
		.build();

		log.info("CourseController::getCourse by id {} response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<APIResponse<List<courseResponseDTO>>> getAllCourses(
	@RequestParam(defaultValue = "0") int page,
	@RequestParam(defaultValue = "10") int size,
	@RequestParam(defaultValue = "id") String sortBy,
	@RequestParam(defaultValue = "asc") String sortDirection,
	@RequestParam(defaultValue = "") String filter,
	@RequestParam(required = false) List<String> filters) {

		log.info("CourseController::getAllCourses with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Course> courses = courseService.findAll(page, size, sortBy, sortDirection, filter, filters);

		Page<courseResponseDTO> couseDTO = courses.map(course -> mapper.convertCourseToCourseDTO(course, false, false));

		APIResponse<List<courseResponseDTO>> responseDTO = APIResponse
		.<List<courseResponseDTO>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(couseDTO.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(couseDTO.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		log.info("CourseController::getAllCourses response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateCourse(@PathVariable("id") Long id, @RequestBody courseUpdateDTO updatedCourse) {
		log.info("CourseController::updateCourse with id {} request body {} ", id, mapper.jsonAsString(updatedCourse));
		Course cs = courseService.update(id, updatedCourse);

		APIResponse<Course> responseDTO = APIResponse
		.<Course>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(cs)
		.build();

		log.info("CourseController::updateCourse response {}", mapper.jsonAsString(responseDTO));
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

		log.info("CourseController::deleteCourse by id {}  response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

//	@PatchMapping("/{id}")
//	public ResponseEntity<Void> addClassToCourse(@PathVariable("id") Long courseId, @RequestBody Class classId) {
//		log.info("CourseController:AddClassToCourse execution started.");
//		courseService.addClassToCourse(courseId, classId);
//		return ResponseEntity.ok().build();
//	}
//	
//	@DeleteMapping("/class/{id}")
//	public ResponseEntity<Void> removeClassFromCourse(@PathVariable("id") Long courseId, @RequestBody Long classId) {
//		log.info("CourseController:removeClassFromCourse execution started.");
//		courseService.RemoveClassFromCourse(courseId, classId);
//		return ResponseEntity.ok().build();
//	}
}
