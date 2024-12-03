package com.SchoolBack.Util;

import com.SchoolBack.Entity.Course;
import com.SchoolBack.Entity.Student;
import com.SchoolBack.Entity.Teacher;
import com.SchoolBack.Entity.User;
import com.SchoolBack.Entity.Class;
import com.SchoolBack.Enum.Role;
import com.SchoolBack.DTO.classResponseDTO;
import com.SchoolBack.DTO.Request.Class.classUpdateDTO;
import com.SchoolBack.DTO.courseResponseDTO;
import com.SchoolBack.DTO.Request.Course.courseUpdateDTO;
import com.SchoolBack.DTO.enrollmentClassStudentResponseDTO;
import com.SchoolBack.DTO.registerUser;
import com.SchoolBack.DTO.Request.Student.studentUpdateDTO;
import com.SchoolBack.DTO.studentResponseDTO;
import com.SchoolBack.DTO.Request.Teacher.teacherUpdateDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.SchoolBack.DTO.teacherResponseDTO;
import java.util.HashSet;
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

	public studentResponseDTO convertStudentToStudentDTO(Student student, boolean loadClass, boolean loadActiveData) {

		Set<enrollmentClassStudentResponseDTO> enrollmentDTOs = new HashSet<>();;
		if (loadClass) {
			enrollmentDTOs = student.getEnrollments().stream()
			.filter(enrollment -> !loadActiveData || enrollment.isActive())
			.map(enrollment -> enrollmentClassStudentResponseDTO.builder()
			.id(enrollment.getId())
			.status(enrollment.getStatus())
			.student(null)
			.aClass(this.convertClassToClassDTO(enrollment.getAClass(), false, true))
			.build()
			)
			.collect(Collectors.toSet());
		}

		return studentResponseDTO.builder()
		.id(student.getId())
		.name(student.getName())
		.major(student.getMajor())
		.email(student.getUser().getEmail())
		.enrollment(enrollmentDTOs)
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

	public courseResponseDTO convertCourseToCourseDTO(Course course, boolean noLazyLoad, boolean loadActiveData) {
		Set<classResponseDTO> classDTOs = new HashSet<>();
		if (noLazyLoad) {
			course.getClasses().stream()
			.filter(aClass -> !loadActiveData || aClass.isActive())
			.map(aClass -> this.convertClassToClassDTO(aClass, true, false))
			.forEach(classDTOs::add); 
		}
		System.out.println(classDTOs.size());
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

	public classResponseDTO convertClassToClassDTO(Class aClass, boolean loadStudent, boolean loadActiveData) {
		Set<enrollmentClassStudentResponseDTO> enrollmentDTOs = new HashSet<>();
		if (loadStudent) {
			enrollmentDTOs = aClass.getEnrollments().stream()
			.filter(enrollment -> !loadActiveData || enrollment.isActive())
			.map(enrollment -> enrollmentClassStudentResponseDTO.builder()
			.id(enrollment.getId())
			.status(enrollment.getStatus())
			.student(this.convertStudentToStudentDTO(enrollment.getStudent(), false, false))
			.aClass(null)
			.build()
			)
			.collect(Collectors.toSet());
		}

		return classResponseDTO.builder()
		.id(aClass.getId())
		.name(aClass.getName())
		.schedule(aClass.getSchedule())
		.totalStudent(enrollmentDTOs.size())
		.teacher(this.convertTeacherToTeacherDTO(aClass.getTeacher()))
		.courseCode(aClass.getCourse().getCourseCode())
		.enrollments(enrollmentDTOs)
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
