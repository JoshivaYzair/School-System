package com.SchoolBack.Util;

import com.SchoolBack.Entity.Course;
import com.SchoolBack.Entity.Grade;
import com.SchoolBack.Entity.School;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Entity.User;
import com.SchoolBack.Enum.Role;
import com.SchoolBack.Model.courseDTO;
import com.SchoolBack.Model.gradeDTO;
import com.SchoolBack.Model.studentDTO;
import com.SchoolBack.Model.teacherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;

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
	
	public static Grade  convertGradeDTOToGrade(gradeDTO gradeRequestDTO, Course course, Student student){
		
		Grade grade = Grade.builder()
		.grade(gradeRequestDTO.getGrade())
		.date(LocalDate.now())
		.isActive(true)
		.student(student)
		.course(course)
		.build();
		
		return grade;
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
	
	public static Grade updateGradeFromDTO(Grade gradeToUpdate, gradeDTO updateRequest) {
		gradeToUpdate.setGrade(updateRequest.getGrade());
		return gradeToUpdate;
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
