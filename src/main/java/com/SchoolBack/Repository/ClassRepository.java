package com.SchoolBack.Repository;

import com.SchoolBack.Entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository  extends JpaRepository<Class, Long>,  JpaSpecificationExecutor<Class>{
	
}