package com.SchoolBack.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.SchoolBack.Entity.Enrollment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long>, JpaSpecificationExecutor<Enrollment>{
	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.aClass.id = :classId")
	Enrollment findByStudentAndClass(@Param("studentId") Long studentId, @Param("classId") Long classId);
}
