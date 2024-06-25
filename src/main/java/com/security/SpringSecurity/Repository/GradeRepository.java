package com.security.SpringSecurity.Repository;

import com.security.SpringSecurity.Entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long>{
	
}