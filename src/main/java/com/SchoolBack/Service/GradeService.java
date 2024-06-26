package com.SchoolBack.Service;

import com.SchoolBack.Entity.Course;
import com.SchoolBack.Repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.SchoolBack.Entity.Grade;
import com.SchoolBack.Exception.CourseNotFoundException;
import com.SchoolBack.Exception.CourseServiceBusinessException;
import com.SchoolBack.Exception.GradeNotFoundException;
import com.SchoolBack.Exception.GradeServiceBusinessException;
import com.SchoolBack.Model.courseDTO;
import com.SchoolBack.Model.gradeDTO;
import com.SchoolBack.Util.GenericSpecifications;
import com.SchoolBack.Util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
@Slf4j
@RequiredArgsConstructor
public class GradeService {

	private final GradeRepository repository;
	private final StudentService studentService;
	private final CourseService courseService;

	public Grade findById(Long id) {
		Grade grade;
		try {
			log.info("GradeService:findByID execution started.");
			Grade gd = repository.findById(id).orElseThrow(
			() -> new CourseNotFoundException("Grade not found with id: " + id));

			if (!gd.isActive()) {
				log.warn("Grade with id {} is not active", id);
				throw new GradeNotFoundException("Grade not active with id: " + id);
			}
			log.debug("GradeService:findById retrieving course from database for id {} {}", id, ValueMapper.jsonAsString(gd));
			grade = gd;
		} catch (Exception e) {
			log.error("Exception occurred while retrieving Grade {} from database , Exception message {}", e.getMessage());
			throw new GradeServiceBusinessException("Exception occurred while fetch a Grade from database:" + e.getMessage());
		}
		log.info("GradeService:findById execution ended.");
		return grade;
	}

	public Grade save(gradeDTO entity) {
		Grade gradeResult;
		var course = courseService.findById(entity.getCourseId());
		var student = studentService.findById(entity.getStudentId());
		try {
			log.info("GradeService:save execution started.");
			Grade grade = ValueMapper.convertGradeDTOToGrade(entity, course, student);
			log.debug("GradeService:save request parameters {}", ValueMapper.jsonAsString(grade));
			Grade gd = repository.save(grade);
			gradeResult = gd;
			log.debug("GradeService:save received response from Database {}", ValueMapper.jsonAsString(gd));
		} catch (Exception e) {
			log.error("Exception occurred while persisting grade to database , Exception message {}", e.getMessage());
			throw new GradeServiceBusinessException("Exception occurred while create a new grade");
		}
		log.info("GradeService:save execution ended.");
		return gradeResult;
	}

	public Grade update(Long id, gradeDTO entity) {
		Grade gradeResult;
		try {
			log.info("GradeService:updateGrade execution started.");
			Grade gradeToUpdate = this.findById(id);
			Grade grade = ValueMapper.updateGradeFromDTO(gradeToUpdate, entity);
			log.debug("GradeService:grade request parameters {}", ValueMapper.jsonAsString(grade));
			Grade gd = repository.save(grade);
			gradeResult = gd;
			log.debug("GradeService:update received response from Database {}", ValueMapper.jsonAsString(gradeResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting grade to database , Exception message {}", e.getMessage());
			throw new GradeServiceBusinessException("Exception occurred while update a grade: " + e.getMessage());
		}
		log.info("GradeService:grade execution ended.");
		return gradeResult;
	}

	public void deleteById(Long id) {
		try {
			log.debug("GradeService:deleteById execution started.");
			Grade grade = this.findById(id);
			log.debug("GradeService:deleteById received response with id {} parameters {}", id, ValueMapper.jsonAsString(grade));
			grade.setActive(false);
			repository.save(grade);
		} catch (Exception e) {
			log.error("Exception occurred while deleting grade from database , Exception message {}", e.getMessage());
			throw new GradeServiceBusinessException("Exception occurred while delete a grade from database:  " + e.getMessage());
		}
		log.info("GradeService:deleteById execution ended.");
	}

	public Page<Grade> findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Grade> gradeResult;
		try {
			log.info("GradeService:findAll execution started.");
			log.debug("GradeService:findAll request parameters :page {} size {} sortBy {} sortDirection {}", page,size,sortBy,sortDirection);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
			gradeResult = repository.findAll(GenericSpecifications.isActive(), pageable);
			log.debug("GradeService:findAll received response from Database {}", ValueMapper.jsonAsString(gradeResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting grade to database , Exception message {}", e.getMessage());
			throw new CourseServiceBusinessException("Exception occurred while fetch a grade: " + e.getMessage());
		}
		log.info("GradeService:findAll execution ended.");
		return gradeResult;
	}
}
