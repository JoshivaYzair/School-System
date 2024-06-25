package com.security.SpringSecurity.Service;

import com.security.SpringSecurity.Repository.SchoolRepository;
import java.util.Optional;
import com.security.SpringSecurity.Entity.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {
	
	private final SchoolRepository repository;
	
	public Optional<School> findById(Long id) {
		return repository.findById(id);
	}
	
}
