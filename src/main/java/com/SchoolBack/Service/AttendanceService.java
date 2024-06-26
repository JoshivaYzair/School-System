package com.SchoolBack.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.SchoolBack.Entity.Attendance;
import com.SchoolBack.Repository.AttendanceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class AttendanceService {
	
	private final AttendanceRepository repository;
	
	public List<Attendance> findAll() {
		return repository.findAll();
	}

	public Optional<Attendance> findById(Long id) {
		return repository.findById(id);
	}

	public Attendance save(Attendance entity) {
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

	public Page<Attendance> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
}
