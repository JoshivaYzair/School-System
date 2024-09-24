package com.SchoolBack.Service;

import com.SchoolBack.Repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import com.SchoolBack.Entity.Enrollment;
import com.SchoolBack.Exception.StudentNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnrollmentService {
	
	private final EnrollmentRepository repository;
	
	public Enrollment findByStudentAndClass(Long studentID, Long classID){
		Enrollment enrollment = null;
		try {
			log.debug("EnrollmentService:findByStudentAndClass execution started.");
			enrollment = repository.findByStudentAndClass(studentID, classID);
			if (enrollment == null || !enrollment.isActive()){
				log.warn("Enrollment not found");
				throw new EntityNotFoundException("No class is associated with this student" );
			}
		} catch (Exception e) {
			log.error("Exception occurred while retrieving enrollment from database , Exception message {}", e.getMessage());
		}
		log.info("EnrollmentService:findByStudentAndClass execution ended.");
		return enrollment;
	}
	
	public void save(Enrollment entity) {
		try {
			log.info("EnrollmentService:save execution started.");
			repository.save(entity);
			log.debug("EnrollmentService:saveEnrollment");
		} catch (Exception e) {
			log.error("Exception occurred while persisting enrollment to database , Exception message {}", e.getMessage());
		}
		log.info("EnrollmentService:createNewCourse execution ended.");
	}
	
	public void deleteById(Enrollment enrollment) {
		try {
			log.debug("EnrollmentService:deleteById execution started.");
			repository.delete(enrollment);
		} catch (Exception e) {
			log.error("Exception occurred while deleting enrollment from database , Exception message {}", e.getMessage());
		}
		log.info("EnrollmentService:deleteById execution ended.");
	}
//	
//	public void removeById(Long id) {
//		try {
//			log.debug("EnrollmentService:deleteById execution started.");
//			Enrollment enrollment = repository.findById(id).orElseThrow(
//			() -> new EntityNotFoundException("Enrollment not found with id: " + id));
//			enrollment.setActive(false);
//			repository.save(enrollment);
//		} catch (Exception e) {
//			log.error("Exception occurred while deleting enrollment from database , Exception message {}", e.getMessage());
//		}
//		log.info("EnrollmentService:deleteById execution ended.");
//	}
}
