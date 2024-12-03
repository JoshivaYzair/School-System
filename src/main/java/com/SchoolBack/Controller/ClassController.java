package com.SchoolBack.Controller;

import com.SchoolBack.DTO.APIResponse;
import com.SchoolBack.DTO.classResponseDTO;
import com.SchoolBack.Service.ClassService;
import com.SchoolBack.Util.ValueMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.SchoolBack.Entity.Class;
import com.SchoolBack.DTO.Request.Class.classUpdateDTO;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/class")
@RequiredArgsConstructor
@Slf4j
public class ClassController {

	private final ClassService classService;
	private final ValueMapper mapper;

	@PostMapping
	public ResponseEntity<APIResponse> saveClass(@Valid @RequestBody classUpdateDTO aClass) {
		log.info("ClassController::createNewClassrequest body {}", mapper.jsonAsString(aClass));
		Class cs = classService.save(aClass);

		APIResponse<Class> responseDTO = APIResponse
		.<Class>builder()
		.status(HttpStatus.CREATED.getReasonPhrase())
		.results(cs)
		.build();

		log.info("ClassController::createNewClass response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<APIResponse> getClass(@PathVariable("id") Long id) {
		log.info("CourseController::getCourse by id  {}", id);
		Class cs = classService.findById(id);

		classResponseDTO classDTO = mapper.convertClassToClassDTO(cs, true,true);
		
		APIResponse<classResponseDTO> responseDTO = APIResponse
		.<classResponseDTO>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(classDTO)
		.build();

		log.info("ClassController::getClassby id {} response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<APIResponse<List<classResponseDTO>>> getAllClases(
	@RequestParam(defaultValue = "0") int page,
	@RequestParam(defaultValue = "10") int size,
	@RequestParam(defaultValue = "id") String sortBy,
	@RequestParam(defaultValue = "asc") String sortDirection) {

		log.info("ClassController::getAllClasses with page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
		Page<Class> classes = classService.findAll(page, size, sortBy, sortDirection);

		Page<classResponseDTO> classDTO = classes.map(aClass -> mapper.convertClassToClassDTO(aClass,false,true));

		APIResponse<List<classResponseDTO>> responseDTO = APIResponse
		.<List<classResponseDTO>>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(classDTO.getContent())
		.Page(page)
		.PageSize(size)
		.totalElements(classDTO.getTotalElements())
		.sortBy(sortBy)
		.sortDirection(sortDirection)
		.build();

		log.info("ClassController::getAllClasses response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<APIResponse> updateClass(@PathVariable("id") Long id, @RequestBody classUpdateDTO updatedClass) {
		log.info("ClassController::updateClass with id {} request body {} ", id, mapper.jsonAsString(updatedClass));
		Class cs = classService.update(id, updatedClass);

		APIResponse<Class> responseDTO = APIResponse
		.<Class>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.results(cs)
		.build();

		log.info("ClassController::updateClass response {}", mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteClass(@PathVariable("id") Long id) {
		log.info("ClassController::deleteClass by id  {}", id);
		classService.deleteById(id);

		APIResponse<Class> responseDTO = APIResponse
		.<Class>builder()
		.status(HttpStatus.OK.getReasonPhrase())
		.build();

		log.info("ClassController::deleteClass by id {}  response {}", id, mapper.jsonAsString(responseDTO));
		return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> addStudentsToClass(@PathVariable("id") Long classID, @RequestBody List<Long> studentsID) {
		log.info("ClassController:AddStudentToClass execution started.");
		classService.addStudentToClass(classID, studentsID);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/remove/student/{id}")
	public ResponseEntity<Void> removeStudentFromClass(@PathVariable("id") Long classID, @RequestBody List<Long> studentsID) {
		log.info("ClassController:removeStudentFromClass execution started.");
		classService.removeStudentFromClass(classID, studentsID);
		return ResponseEntity.ok().build();
	}
}
