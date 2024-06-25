package com.security.SpringSecurity.Enum;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public enum Role {
	
	STUDENT(
		getStudentPermissions()
	),
	TEACHER(
		getTeachertPermissions()
	),
	ADMIN(
		getAllPermissions()
	),
	GUEST(
		Set.of(
			Permission.STUDENT_READ
		)
	);
	
	@Getter
	private final Set<Permission> permissions;
	
	public List<SimpleGrantedAuthority> getAuthorities(){
		var authorities = getPermissions()
			.stream()
			.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
			.collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
	
	private static Set<Permission> getAllPermissions() {
		return new HashSet<>(Arrays.asList(Permission.values()));
	}
	
	private static Set<Permission> getStudentPermissions(){
		return Arrays.stream(Permission.values())
				.filter(permission -> permission.name().startsWith("STUDENT"))
				.collect(Collectors.toSet());
	}
	
	private static Set<Permission> getTeachertPermissions(){
		return Arrays.stream(Permission.values())
				.filter(permission -> permission.name().startsWith("TEACHER"))
				.collect(Collectors.toSet());
	} 
}
