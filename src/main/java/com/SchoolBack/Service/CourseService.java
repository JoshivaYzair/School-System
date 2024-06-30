package com.SchoolBack.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.SchoolBack.Entity.Course;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Enrollment;
import com.SchoolBack.Exception.CourseNotFoundException;
import com.SchoolBack.Exception.CourseServiceBusinessException;
import com.SchoolBack.Model.addStudentToCourseDTO;
import com.SchoolBack.Model.courseDTO;
import com.SchoolBack.Repository.CourseRepository;
import com.SchoolBack.Repository.EnrollmentRepository;
import com.SchoolBack.Util.ValueMapper;
import com.SchoolBack.Util.GenericSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {

	private final CourseRepository repository;
	private final EnrollmentRepository enrollmentRepository;
	private final SchoolService schoolService;
	private final TeacherService teacherService;
	private final StudentService studentService;

	public Course findById(Long id) {
		Course course;
		try {
			log.info("CourseService:findByID execution started.");
			Course cs = repository.findById(id).orElseThrow(
			() -> new CourseNotFoundException("Course not found with id: " + id));

			if (!cs.isActive()) {
				log.warn("Course with id {} is not active", id);
				throw new CourseNotFoundException("Course not active with id: " + id);
			}
			log.debug("CourseService:findById retrieving course from database for id {} {}", id, ValueMapper.jsonAsString(cs));
			course = cs;
		} catch (Exception e) {
			log.error("Exception occurred while retrieving Course {} from database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while fetch a Course from database:" + e.getMessage());
		}
		log.info("CourseService:findById execution ended.");
		return course;
	}

	public Course save(courseDTO entity) {
		Course courseResult;
		var teacher = teacherService.findById(entity.getTeacherId());
		var school = schoolService.findById(entity.getSchoolId());
		try {
			log.info("CourseService:save execution started.");
			Course course = ValueMapper.convertCourseDTOToCourse(entity, school.get(), teacher);
			log.debug("CourseService:createNewCourse request parameters {}", ValueMapper.jsonAsString(course));
			Course cs = repository.save(course);
			courseResult = cs;
			log.debug("CourseService:createNewCourse received response from Database {}", ValueMapper.jsonAsString(cs));
		} catch (Exception e) {
			log.error("Exception occurred while persisting course to database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while create a new course");
		}
		log.info("CourseService:createNewCourse execution ended.");
		return courseResult;
	}

	public Course update(Long id, courseDTO entity) {
		Course courseResult;
		try {
			log.info("CourseService:course execution started.");
			Course courseToUpdate = this.findById(id);
			Course course = ValueMapper.updateCourseFromDTO(courseToUpdate, entity);
			log.debug("CourseService:course request parameters {}", ValueMapper.jsonAsString(course));
			Course cs = repository.save(course);
			courseResult = cs;
			log.debug("CourseService:update received response from Database {}", ValueMapper.jsonAsString(courseResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting course to database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while update a course: " + e.getMessage());
		}
		log.info("CourseService:course execution ended.");
		return courseResult;
	}

	public void deleteById(Long id) {
		try {
			log.debug("CourseService:deleteById execution started.");
			Course course = this.findById(id);
			log.debug("CourseService:deleteById received response with id {} parameters {}", id, ValueMapper.jsonAsString(course));
			course.setActive(false);
			repository.save(course);
		} catch (Exception e) {
			log.error("Exception occurred while deleting course from database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while delete a grade from database:  " + e.getMessage());
		}
		log.info("CourseService:deleteById execution ended.");
	}

	public void addStudentToCourse(Long courseId, addStudentToCourseDTO studentList) {
		//Course courseResult;
		log.info("CourseService:AddStudentToCourse execution started.");
		try {
			log.info("CourseService:AddStudentToCourse execution started.");
			Course course = this.findById(courseId);
			for (Long studentId : studentList.getStudentList()) {
				Student student = studentService.findById(studentId);
				var enrollment = new Enrollment();
				enrollment.setCourse(course);
				enrollment.setStudent(student);
				enrollmentRepository.save(enrollment);
			}
		} catch (Exception e) {
			log.error("Exception occurred while persisting new student into course to database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while add a new student in the course");
		}
		log.info("CourseService:AddStudentToCourse execution ended.");
	}
	
	//@Transactional
	public void RemoveStudentFromCourse(Long courseId, addStudentToCourseDTO studentList) {
		log.info("CourseService:RemoveStudentToCourse execution started.");
		Course course = this.findById(courseId);
		Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(studentList.getStudentList().get(0),course.getId());
		try {
			enrollmentRepository.delete(enrollment);
		} catch (Exception e) {
			log.error("Exception occurred while removing a student from course , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while remove a student from course");
		}
		log.info("CourseService:RemoveStudentToCourse execution ended.");
	}

	public Page<Course> findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Course> courseResult;
		try {
			log.info("CourseService:findAll execution started.");
			log.debug("CourseService:findAll request parameters :page {} size {} sortBy {} sortDirection {}", page, size, sortBy, sortDirection);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
			courseResult = repository.findAll(GenericSpecifications.isActive(), pageable);
			log.debug("CourseService:findAll received response from Database {}", ValueMapper.jsonAsString(courseResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting courses to database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while fetch a grade: " + e.getMessage());
		}
		log.info("CourseService:findAll execution ended.");
		return courseResult;
	}
}
