package com.SchoolBack.Util;

import com.SchoolBack.Entity.Course;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Entity.User;
import com.SchoolBack.Entity.Class;
import com.SchoolBack.Enum.Role;
import com.SchoolBack.Model.classResponseDTO;
import com.SchoolBack.Model.classUpdateDTO;
import com.SchoolBack.Model.courseResponseDTO;
import com.SchoolBack.Model.courseUpdateDTO;
import com.SchoolBack.Model.registerUser;
import com.SchoolBack.Model.studentUpdateDTO;
import com.SchoolBack.Model.studentResponseDTO;
import com.SchoolBack.Model.teacherUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.SchoolBack.Model.teacherResponseDTO;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValueMapper {

	private final ObjectMapper objectMapper;

	@Autowired
	public ValueMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public Student convertStudentDTOToStudent(registerUser studentRequestDTO) {
		var user = User.builder()
		.isActive(true)
		.email(studentRequestDTO.getEmail())
		.password(studentRequestDTO.getPassword())
		.role(Role.STUDENT)
		.build();

		return Student.builder()
		.name(studentRequestDTO.getName())
		.major(studentRequestDTO.getMajorOrDepartament())
		.isActive(true)
		.user(user)
		.build();
	}

	public studentResponseDTO convertStudentToStudentDTO(Student student) {

		return studentResponseDTO.builder()
		.id(student.getId())
		.name(student.getName())
		.major(student.getMajor())
		.email(student.getUser().getEmail())
		.build();
	}

	public Teacher convertTeacherDTOToTeacher(registerUser teacherRequestDTO) {
		var user = User.builder()
		.isActive(true)
		.email(teacherRequestDTO.getEmail())
		.password(teacherRequestDTO.getPassword())
		.role(Role.TEACHER)
		.build();

		return Teacher.builder()
		.name(teacherRequestDTO.getName())
		.departament(teacherRequestDTO.getMajorOrDepartament())
		.isActive(true)
		.user(user)
		.build();
	}

	public teacherResponseDTO convertTeacherToTeacherDTO(Teacher teacher) {
		return teacherResponseDTO.builder()
		.id(teacher.getId())
		.name(teacher.getName())
		.departament(teacher.getDepartament())
		.email(teacher.getUser().getEmail())
		.build();
	}

	public User convertRegisterDTOToAdmin(registerUser registerDTO) {
		return User.builder()
		.isActive(true)
		.email(registerDTO.getEmail())
		.password(registerDTO.getPassword())
		.role(Role.ADMIN)
		.build();
	}

	public Course convertCourseDTOToCourse(courseUpdateDTO courseRequestDTO) {
		return Course.builder()
		.name(courseRequestDTO.getName())
		.courseCode(courseRequestDTO.getCourseCode())
		.isActive(true)
		.build();
	}

	public courseResponseDTO convertCourseToCourseDTO(Course course, boolean noLazyLoad) {
		Set<classResponseDTO> classDTOs=null;
		if (noLazyLoad) {
			classDTOs = course.getClasses().stream()
			.map(this::convertClassToClassDTO)
			.collect(Collectors.toSet());
		}
		return courseResponseDTO.builder()
		.id(course.getId())
		.name(course.getName())
		.courseCode(course.getCourseCode())
		.classes(classDTOs)
		.build();
	}

	public Class convertClassDTOToClass(classUpdateDTO classRequestDTO, Teacher teacher, Course course) {
		return Class.builder()
		.name(classRequestDTO.getName())
		.schedule(classRequestDTO.getSchedule())
		.isActive(true)
		.teacher(teacher)
		.course(course)
		.build();
	}

	public classResponseDTO convertClassToClassDTO(Class aClass) {
		return classResponseDTO.builder()
		.id(aClass.getId())
		.name(aClass.getName())
		.schedule(aClass.getSchedule())
		.techaer(aClass.getTeacher().getName())
		.teacherID(aClass.getTeacher().getId())
		.courseCode(aClass.getCourse().getCourseCode())
		.build();
	}

	public Student updateStudentFromDTO(Student studentToUpdate, studentUpdateDTO updateRequest) {
		studentToUpdate.setName(updateRequest.getName());
		studentToUpdate.setMajor(updateRequest.getMajor());
		return studentToUpdate;
	}

	public Teacher updateTeacherFromDTO(Teacher teacherToUpdate, teacherUpdateDTO updateRequest) {
		teacherToUpdate.setName(updateRequest.getName());
		teacherToUpdate.setDepartament(updateRequest.getDepartament());
		return teacherToUpdate;
	}

	public Course updateCourseFromDTO(Course courseToUpdate, courseUpdateDTO updateRequest) {
		courseToUpdate.setName(updateRequest.getName());
		courseToUpdate.setCourseCode(updateRequest.getCourseCode());
		return courseToUpdate;
	}

	public Class updateClassFromDTO(Class classToUpdate, Class updateRequest) {
		classToUpdate.setName(updateRequest.getName());
		classToUpdate.setSchedule(updateRequest.getSchedule());
		classToUpdate.setTeacher(updateRequest.getTeacher());
		return classToUpdate;
	}

	public String jsonAsString(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
