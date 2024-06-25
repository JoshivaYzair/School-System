package com.security.SpringSecurity.Repository;

import org.springframework.stereotype.Repository;
import com.security.SpringSecurity.Entity.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findUserByEmail(String email);
}
