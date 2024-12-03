package com.SchoolBack.Service;

import com.SchoolBack.Repository.ClassRepository;
import com.SchoolBack.Util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.SchoolBack.Exception.ClassNotFoundException;
import com.SchoolBack.Exception.ClassServiceBuisinessException;
import com.SchoolBack.Entity.Enrollment;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Class;
import com.SchoolBack.DTO.Request.Class.classUpdateDTO;
import org.springframework.data.domain.Sort;
import com.SchoolBack.Util.GenericSpecifications;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ClassService {
	
	private final ClassRepository repository;
	private final EnrollmentService enrollmentService;
	private final CourseService courseService;
	private final TeacherService teacherService;
	private final StudentService studentService;
	private final ValueMapper mapper;

	@Transactional
	public Class findById(Long id) {
		Class aClass;
		try {
			log.info("ClassService:findByID execution started.");
			aClass = repository.findById(id).orElseThrow(
			() -> new ClassNotFoundException("Class not found with id: " + id));
			if (!aClass.isActive()) {
				log.warn("Class with id {} is not active", id);
				throw new ClassNotFoundException("Class not active with id: " + id);
			}
		} catch (Exception e) {
			log.error("Exception occurred while retrieving Class {} from database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while fetch a Class from database:" + e.getMessage());
		}
		log.info("ClassService:findById execution ended.");
		return aClass;
	}

	@Transactional
	public Class save(classUpdateDTO entity) {
		Class classResult;
		var teacher = teacherService.findById(entity.getTeacher());
		var course = courseService.findById(entity.getCourse());
		try {
			log.info("ClassService:save execution started.");
			Class aClass = mapper.convertClassDTOToClass(entity, teacher, course);
			log.debug("ClassService:createNewClass request parameters {}", mapper.jsonAsString(aClass));
			course.getClasses().add(aClass);
			teacher.getClasses().add(aClass);
			Class cs = repository.save(aClass);
			classResult = cs;
			log.debug("ClassService:createNewClass received response from Database {}", mapper.jsonAsString(cs));
		} catch (Exception e) {
			log.error("Exception occurred while persisting class to database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while create a new class");
		}
		log.info("ClassService:createNewClass execution ended.");
		return classResult;
	}

	public Class update(Long id, classUpdateDTO entity) {
		Class classResult;
		try {
			log.info("ClassService:course execution started.");
			Class courseToUpdate = this.findById(id);
			var teacher = this.teacherService.findById(entity.getTeacher());
			Class classUpdate = Class.builder()
			.name(entity.getName())
			.schedule(entity.getSchedule())
			.isActive(true)
			.teacher(teacher)
			.build();
			Class course = mapper.updateClassFromDTO(courseToUpdate, classUpdate);
			log.debug("ClassService:Class request parameters {}", mapper.jsonAsString(course));
			Class cs = repository.save(course);
			classResult = cs;
			log.debug("ClassService:update received response from Database {}", mapper.jsonAsString(classResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting class to database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while update a class: " + e.getMessage());
		}
		log.info("ClassService:class execution ended.");
		return classResult;
	}

	public void deleteById(Long id) {
		try {
			log.debug("ClassService:deleteById execution started.");
			Class aClass = this.findById(id);
			log.debug("CourseService:deleteById received response with id {} parameters {}", id, mapper.jsonAsString(aClass));
			aClass.setActive(false);
			repository.save(aClass);
		} catch (Exception e) {
			log.error("Exception occurred while deleting class from database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while delete a class from database:  " + e.getMessage());
		}
		log.info("CourseService:deleteById execution ended.");
	}

	public void addStudentToClass(Long classID, List<Long> studentsListID) {
		log.info("ClassService:AddStudentToClass execution started.");
		try {
			log.info("ClassService:AddStudentToClass execution started.");
			Class aClass = this.findById(classID);
			studentsListID.stream().map(studentId -> {
				Student student = studentService.findStudentById(studentId);
				Enrollment enrollment = new Enrollment();
				enrollment.setAClass(aClass);
				enrollment.setActive(true);
				enrollment.setStudent(student);
				enrollment.setStatus("Taking");
				return enrollment;
			}).forEach(enrollmentService::save);
		} catch (Exception e) {
			log.error("Exception occurred while persisting new student into Class to database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while add a new student in the class");
		}
		log.info("ClassService:AddStudentToClass execution ended.");
	}

	@Transactional
	public void removeStudentFromClass(Long classId, List<Long> studentList) {
		log.info("ClassService:RemoveStudentToClass execution started.");
		Class aClass = this.findById(classId);

		studentList.stream().map(studentId -> enrollmentService.findByStudentAndClass(studentId, aClass.getId()))
		.forEach(enrollment -> {
			try {
				enrollment.setStatus("Removed");
				enrollmentService.deleteById(enrollment);
			} catch (Exception e) {
				log.error("Exception occurred while removing a student from class, Exception message {}", e.getMessage());
				throw new ClassServiceBuisinessException("The student is not enrolled in this class.");
			}
		});
		log.info("ClassService:RemoveStudentToClasse execution ended.");
	}

	public Page<Class> findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Class> classResult;
		try {
			log.info("ClassService:findAll execution started.");
			log.debug("ClassService:findAll request parameters :page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
			classResult = repository.findAll(GenericSpecifications.isActive(), pageable);
			log.debug("ClassService:findAll received response from Database {}", mapper.jsonAsString(classResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting class to database , Exception message {}", e.getMessage());
			throw new ClassServiceBuisinessException("Exception occurred while fetch a grade: " + e.getMessage());
		}
		log.info("ClassService:findAll execution ended.");
		return classResult;
	}
}
