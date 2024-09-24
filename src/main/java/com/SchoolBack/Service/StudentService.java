package com.SchoolBack.Service;

import com.SchoolBack.Repository.StudentRepository;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Exception.StudentNotFoundException;
import com.SchoolBack.Exception.StudentServiceBusinessException;
import com.SchoolBack.Model.studentUpdateDTO;
import com.SchoolBack.Util.GenericSpecifications;
import com.SchoolBack.Util.ValueMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

	private final StudentRepository repository;
	private final ValueMapper mapper;

	public Student findStudentById(Long id) {
		Student student;
		try {
			log.info("StudentService:findByID execution started.");
			Student st = repository.findById(id).orElseThrow(
			() -> new StudentNotFoundException("Student not found with id: " + id));
			if (!st.isActive()) {
				log.warn("Student with id {} is not active", id);
				throw new StudentNotFoundException("Student not active with id: " + id);
			}
			log.debug("StudentService:findById retrieving student from database for id");
			student = st;
		} catch (Exception e) {
			log.error("Exception occurred while retrieving student from database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while fetch a student from database:" + e.getMessage());
		}
		log.info("StudentService:findById execution ended.");
		return student;
	}

	public Student save(Student entity) {
		Student studentResult;
		try {
			log.info("StudentService:save execution started.");
			log.debug("StudentService:createNewStudent request parameters {}", mapper.jsonAsString(entity));
			Student st = repository.save(entity);
			studentResult = st;
			log.debug("StudentService:createNewStudent received response from Database {}", mapper.jsonAsString(st));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while create a new student");
		}
		log.info("StudentService:createNewStudent execution ended.");
		return studentResult;
	}

	public void addCourseToStudent(Student student) {
		repository.save(student);
	}

	public Student update(Long id, studentUpdateDTO entity) {
		Student studentResult;
		try {
			log.info("StudentService:update execution started.");
			Student studentToUpdate = this.findStudentById(id);
			Student student = mapper.updateStudentFromDTO(studentToUpdate, entity);
			log.debug("StudentService:update request parameters {}", mapper.jsonAsString(student));
			Student st = repository.save(student);
			studentResult = st;
			log.debug("StudentService:update received response from Database {}", mapper.jsonAsString(st));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while update a student: " + e.getMessage());
		}
		log.info("StudentService:updateexecution ended.");
		return studentResult;
	}

	public void deleteById(Long id) {
		try {
			Student st = this.findStudentById(id);
			log.debug("StudentService:deleteById retrieving student from database for id {} {}", id, mapper.jsonAsString(st));
			st.setActive(false);
			st.getUser().setActive(false);
			repository.save(st);
		} catch (Exception e) {
			log.error("Exception occurred while deleting  student from database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while delete a student from database: " + e.getMessage());
		}
		log.info("StudentService:deleteById execution ended.");
	}

	public Page<Student> findAll(int page, int size, String sortBy, String sortDirection, String filterName, List<String> majors) {
		Page<Student> studentResult;
		try {
			log.info("StudentService:findAll execution started.");
			log.debug("StudentService:findAll request parameters :page {} size {} sortBy {} sortDirection {} filter {}", page, size, sortBy, sortDirection, filterName);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Specification<Student> spec = GenericSpecifications.isActive();
			spec = spec.and(GenericSpecifications.hasNameOrEmail(filterName));
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

			if (majors != null && !majors.isEmpty()) {
				spec = spec.and(GenericSpecifications.hasFieldInList("major", majors));
			}
			studentResult = repository.findAll(spec, pageable);
			//log.debug("StudentService:findAll received response from Database {}", mapper.jsonAsString(studentResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while fetch a student: " + e.getMessage());
		}
		log.info("StudentService:findAll execution ended.");
		return studentResult;
	}
}
