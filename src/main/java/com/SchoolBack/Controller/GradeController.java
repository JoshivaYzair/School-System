package com.SchoolBack.Controller;

import com.SchoolBack.Entity.Course;
import com.SchoolBack.Model.APIResponse;
import com.SchoolBack.Model.gradeDTO;
import com.SchoolBack.Service.GradeService;
import com.SchoolBack.Util.ValueMapper;
import jakarta.validation.Valid;
import com.SchoolBack.Entity.Grade;
import com.SchoolBack.Model.PagedResponseDTO;
import com.SchoolBack.Model.courseDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/grade")
@Slf4j
public class GradeController {
	
	private final GradeService gradeService;
	
	@PostMapping
	public ResponseEntity<APIResponse> saveGrade (@Valid @RequestBody gradeDTO grade){
		log.info("GradeController::createNewGrade request body {}", ValueMapper.jsonAsString(grade));
		
		Grade gd = gradeService.save(grade);
		
		APIResponse<Grade> responseDTO = APIResponse
		.<Grade>builder()
		.status(HttpStatus.CREATED.getReasonPhrase())
		.results(gd)
		.build();
		
		log.info("GradeController::createNewGrade response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getGrade(@PathVariable("id") Long id) {
		log.info("GradeController::getGrade by id  {}", id);
		Grade gd = gradeService.findById(id);

		APIResponse<Grade> responseDTO = APIResponse
		.<Grade>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(gd)
		.build();

		log.info("GradeController::getGrade by id {} response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<APIResponse<PagedResponseDTO<Grade>>> getAllCourses(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		@RequestParam(defaultValue = "id") String sortBy,
		@RequestParam(defaultValue = "asc") String sortDirection) {

		log.info("GradeController::getAllGrades with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Grade> grades = gradeService.findAll(page, size, sortBy, sortDirection);

		PagedResponseDTO<Grade> pagedResponseDTO = PagedResponseDTO.<Grade>builder()
		.content(grades.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(grades.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		APIResponse<PagedResponseDTO<Grade>> responseDTO = APIResponse
		.<PagedResponseDTO<Grade>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(pagedResponseDTO)
		.build();

		log.info("GradeController::getAllGrades response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateCourse(@PathVariable("id") Long id, @RequestBody gradeDTO updatedGrade) {
		log.info("GradeController::updateGrade with id {} request body {} ", id, ValueMapper.jsonAsString(updatedGrade));
		Grade gd = gradeService.update(id, updatedGrade);

		APIResponse<Grade> responseDTO = APIResponse
		.<Grade>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(gd)
		.build();

		log.info("GradeController::updateGrade response {}", ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity deleteCourse(@PathVariable("id") Long id) {
		log.info("GradeController::deleteGrade by id  {}", id);
		gradeService.deleteById(id);

		APIResponse<Grade> responseDTO = APIResponse
		.<Grade>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();

		log.info("GradeController::deleteGrade by id {}  response {}", id, ValueMapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}
}
