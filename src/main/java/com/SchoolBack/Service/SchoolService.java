package com.SchoolBack.Service;

import com.SchoolBack.Repository.SchoolRepository;
import java.util.Optional;
import com.SchoolBack.Entity.School;
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
