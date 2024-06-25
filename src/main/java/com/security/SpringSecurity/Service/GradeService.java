package com.security.SpringSecurity.Service;

import com.security.SpringSecurity.Repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.security.SpringSecurity.Entity.Grade;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
@RequiredArgsConstructor
public class GradeService {
	
	private final GradeRepository repository;
	
	public List<Grade> findAll() {
		return repository.findAll();
	}

	public Optional<Grade> findById(Long id) {
		return repository.findById(id);
	}

	public Grade save(Grade entity) {
		return repository.save(entity);
	}

//	public void deleteById(Long id) {
//		Optional<Grade> obj = repository.findById(id);
//		if (obj.isPresent()) {
//			Grade entity = obj.get();
//			entity.setActive(false);
//			repository.save(entity);
//		}
//	}

	public Page<Grade> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
