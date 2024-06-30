package com.SchoolBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SchoolBack.Entity.Enrollment;
import org.springframework.data.jpa.repository.Query;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>{
	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
	Enrollment findByStudentAndCourse(Long studentId, Long courseId);
}
