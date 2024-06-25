package com.security.SpringSecurity.Util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.SpringSecurity.Entity.Course;
import com.security.SpringSecurity.Entity.School;
import com.security.SpringSecurity.Entity.Student;
import com.security.SpringSecurity.Entity.Teacher;
import com.security.SpringSecurity.Entity.User;
import com.security.SpringSecurity.Enum.Role;
import com.security.SpringSecurity.Model.courseDTO;
import com.security.SpringSecurity.Model.studentDTO;
import com.security.SpringSecurity.Model.teacherDTO;

public class ValueMapper {

	public static Student convertStudentDTOToStudent(studentDTO studentRequestDTO) {

		var user = User.builder()
		.isActive(true)
		.email(studentRequestDTO.getEmail())
		.password(studentRequestDTO.getPassword())
		.role(Role.STUDENT)
		.build();

		Student student = Student.builder()
		.name(studentRequestDTO.getName())
		.isActive(true)
		.user(user)
		.build();

		return student;
	}
	
	public static Teacher convertTeacherDTOToTeacher(teacherDTO teacherRequestDTO){
		
		var user = User.builder()
		.isActive(true)
		.email(teacherRequestDTO.getEmail())
		.password(teacherRequestDTO.getPassword())
		.role(Role.TEACHER)
		.build();
		
		Teacher teacher = Teacher.builder()
		.name(teacherRequestDTO.getName())
		.departament(teacherRequestDTO.getDepartament())
		.isActive(true)
		.user(user)
		.build();
		
		return teacher;
	}
	
	public static User convertTeacherDTOToTeacher(studentDTO teacherRequestDTO){
		var user = User.builder()
		.isActive(true)
		.email(teacherRequestDTO.getEmail())
		.password(teacherRequestDTO.getPassword())
		.role(Role.ADMIN)
		.build();
		
		return user;
	}
	
	public static Course  convertCourseDTOToCourse(courseDTO courseRequestDTO, School school, Teacher teacher){
		
		Course course = Course.builder()
		.name(courseRequestDTO.getName())
		.schedule(courseRequestDTO.getSchedule())
		.courseCode(courseRequestDTO.getCourseCode())
		.isActive(true)
		.teacher(teacher)
		.school(school)
		.build();
		
		return course;
	}

	public static Student updateStudentFromDTO(Student studentToUpdate,studentDTO updateRequest) {
		studentToUpdate.setName(updateRequest.getName());
		studentToUpdate.setGrade(updateRequest.getGrade());
		return studentToUpdate;
	}
	
	public static Teacher updateTeacherFromDTO(Teacher teacherToUpdate,teacherDTO updateRequest) {
		teacherToUpdate.setName(updateRequest.getName());
		teacherToUpdate.setDepartament(updateRequest.getDepartament());
		return teacherToUpdate;
	}
	
	public static Course updateCourseFromDTO(Course courseToUpdate, courseDTO updateRequest) {
		courseToUpdate.setName(updateRequest.getName());
		courseToUpdate.setSchedule(updateRequest.getSchedule());
		courseToUpdate.setCourseCode(updateRequest.getCourseCode());
		return courseToUpdate;
	}

	public static String jsonAsString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
