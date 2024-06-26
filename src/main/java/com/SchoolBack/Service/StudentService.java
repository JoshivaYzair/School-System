package com.SchoolBack.Service;

import com.SchoolBack.Repository.StudentRepository;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Exception.StudentNotFoundException;
import com.SchoolBack.Exception.StudentServiceBusinessException;
import com.SchoolBack.Model.studentDTO;
import com.SchoolBack.Util.GenericSpecifications;
import com.SchoolBack.Util.ValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

	private final StudentRepository repository;

	public Student findById(Long id) {
		Student student;
		try {
			log.info("StudentService:findByID execution started.");
			Student st = repository.findById(id).orElseThrow(
			() -> new StudentNotFoundException("Student not found with id: " + id));

			if (!st.isActive()) {
				log.warn("Student with id {} is not active", id);
				throw new StudentNotFoundException("Student not active with id: " + id);
			}
			log.debug("StudentService:findById retrieving student from database for id {} {}", id, ValueMapper.jsonAsString(st));
			student = st;
		} catch (Exception e) {
			log.error("Exception occurred while retrieving student {} from database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while fetch a student from database:" + e.getMessage());
		}
		log.info("StudentService:findById execution ended.");
		return student;
	}

	public Student save(studentDTO entity) {
		//Optional<School> school = schoolService.findById(entity.getSchoolId());
		Student studentResult;
		try {
			log.info("StudentService:save execution started.");
			Student student = ValueMapper.convertStudentDTOToStudent(entity);
			log.debug("StudentService:createNewStudent request parameters {}", ValueMapper.jsonAsString(student));
			Student st = repository.save(student);
			studentResult = st;
			log.debug("StudentService:createNewStudent received response from Database {}", ValueMapper.jsonAsString(st));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while create a new student");
		}
		log.info("StudentService:createNewStudent execution ended.");
		return studentResult;
	}

	public Student update(Long id, studentDTO entity) {
		Student studentResult;
		try {
			log.info("StudentService:update execution started.");
			Student studentToUpdate = this.findById(id);
			Student student = ValueMapper.updateStudentFromDTO(studentToUpdate, entity);
			log.debug("StudentService:update request parameters {}", ValueMapper.jsonAsString(student));
			Student st = repository.save(student);
			studentResult = st;
			log.debug("StudentService:update received response from Database {}", ValueMapper.jsonAsString(st));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while update a student: " + e.getMessage());
		}
		log.info("StudentService:updateexecution ended.");
		return studentResult;
	}

	public void deleteById(Long id) {
		try {
			Student st = this.findById(id);
			log.debug("StudentService:deleteById retrieving student from database for id {} {}", id, ValueMapper.jsonAsString(st));
			st.setActive(false);
			repository.save(st);
		} catch (Exception e) {
			log.error("Exception occurred while deleting  student from database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while delete a student from database: " + e.getMessage());
		}
		log.info("StudentService:deleteById execution ended.");
	}

	public Page<Student> findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Student> studentResult;
		try {
			log.info("StudentService:findAll execution started.");
			log.debug("StudentService:findAll request parameters :page {} size {} sortBy {} sortDirection {}", page,size,sortBy,sortDirection);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
			studentResult = repository.findAll(GenericSpecifications.isActive(), pageable);
			log.debug("StudentService:findAll received response from Database {}", ValueMapper.jsonAsString(studentResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new StudentServiceBusinessException("Exception occurred while fetch a student: " + e.getMessage());
		}
		log.info("StudentService:findAll execution ended.");
		return studentResult;
	}
}
