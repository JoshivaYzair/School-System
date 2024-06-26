package com.SchoolBack.Enum;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
	ADMIN_CREATE("admin_create"),
	ADMIN_UPDATE("admin_update"),
	ADMIN_DELETE("admin_delete"),
	ADMIN_READ("admin_read"),
	
	TEACHER_CREATE("teacher_create"),
	TEACHER_UPDATE("teacher_update"),
	TEACHER_DELETE("teacher_delete"),
	TEACHER_READ("teacher_read"),
	
	STUDENT_CREATE("student_create"),
	STUDENT_UPDATE("student_update"),
	STUDENT_READ("student_read");
	
	@Getter
	private final String permission;
}
