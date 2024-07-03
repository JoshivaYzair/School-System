package com.SchoolBack.Service;

import com.SchoolBack.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Exception.TeacherNotFoundException;
import com.SchoolBack.Exception.TeacherServiceBusinessException;
import com.SchoolBack.Model.teacherDTO;
import com.SchoolBack.Util.ValueMapper;
import com.SchoolBack.Util.GenericSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherService {
	
	private final TeacherRepository repository;

	public Teacher findById(Long id) {
		Teacher teacher;
		try {
			log.info("TeacherService:findByID execution started.");
			Teacher th = repository.findById(id).orElseThrow(
			() -> new TeacherNotFoundException("Teacher not found with id: " + id));
			
			if (!th.isActive()) {
				log.warn("Teacher with id {} is not active", id);
				throw new TeacherNotFoundException("Teacher not active with id: " + id);
			}
			log.debug("TeacherService:findById retrieving student from database for id {} {}", id, ValueMapper.jsonAsString(th));
			teacher = th;
		} catch (Exception e) {
			log.error("Exception occurred while retrieving Teacher {} from database , Exception message {}", e.getMessage());
			throw new TeacherServiceBusinessException("Exception occurred while fetch a teacher from database:" + e.getMessage());
		}
		log.info("TeacherService:findById execution ended.");
		return teacher;
	}

	public Teacher save(teacherDTO entity) {
		Teacher teacherResult;
		try {
			log.info("TeacherService:save execution started.");
			Teacher teacher = ValueMapper.convertTeacherDTOToTeacher(entity);
			log.debug("TeacherService:createNewTeacher request parameters {}", ValueMapper.jsonAsString(teacher));
			Teacher th = repository.save(teacher);
			teacherResult = th;
			log.debug("TeacherService:createNewTeacher received response from Database {}", ValueMapper.jsonAsString(th));
		} catch (Exception e) {
			log.error("Exception occurred while persisting teacher to database , Exception message {}", e.getMessage());
			throw new TeacherServiceBusinessException("Exception occurred while create a new teacher");
		}
		log.info("TeacherService:createNewTeacher execution ended.");
		return teacherResult;
	}
	
	public Teacher update(Long id, teacherDTO entity) {
		Teacher teacherResult;
		try {
			log.info("TeacherService:update execution started.");
			Teacher teacherToUpdate = this.findById(id);
			Teacher teacher = ValueMapper.updateTeacherFromDTO(teacherToUpdate, entity);
			log.debug("TeacherService:update request parameters {}", ValueMapper.jsonAsString(teacher));
			Teacher th = repository.save(teacher);
			teacherResult = th;
			log.debug("TeacherService:update received response from Database {}", ValueMapper.jsonAsString(teacherResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting student to database , Exception message {}", e.getMessage());
			throw new TeacherServiceBusinessException("Exception occurred while update a teacher: " + e.getMessage());
		}
		log.info("TeacherService:update execution ended.");
		return teacherResult;
	}

	public void deleteById(Long id) {
		try{
			log.debug("TeacherService:deleteById execution started.");
			Teacher teacher = this.findById(id);
			log.debug("TeacherService:deleteById received response with id {} parameters {}", id,ValueMapper.jsonAsString(teacher));
			teacher.setActive(false);
			repository.save(teacher);
		}catch (Exception e){
			log.error("Exception occurred while deleting teacher from database , Exception message {}", e.getMessage());
			throw new TeacherServiceBusinessException("Exception occurred while delete a teacher from database:  " + e.getMessage());
		}
		log.info("StudentService:deleteById execution ended.");
	}

	public Page<Teacher> findAll(int page, int size, String sortBy, String sortDirection) {
		Page<Teacher> teacherResult;
		try {
			log.info("TeacherService:findAll execution started.");
			log.debug("TeacherService:findAll request parameters :page {} size {} sortBy {} sortDirection {}", page,size,sortBy,sortDirection);
			Sort.Direction direction = Sort.Direction.fromString(sortDirection);
			Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
			teacherResult = repository.findAll(GenericSpecifications.isActive(), pageable);
			log.debug("TeacherService:findAll received response from Database {}", ValueMapper.jsonAsString(teacherResult));
		} catch (Exception e) {
			log.error("Exception occurred while persisting teacher to database , Exception message {}", e.getMessage());
			throw new TeacherServiceBusinessException("Exception occurred while fetch a teacher: " + e.getMessage());
		}
		log.info("TeacherService:findAll execution ended.");
		return teacherResult;
	}
}
