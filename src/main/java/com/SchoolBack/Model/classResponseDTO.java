package com.SchoolBack.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.SchoolBack.Entity.Enrollment;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import lombok.Builder;
import lombok.Data;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class classResponseDTO {
	private Long id;
	private String name;
	private String schedule;
	private String courseCode;
	//private String techaer;
	//private long teacherID;
	private int totalStudent;
	private teacherResponseDTO teacher;
	private Set<enrollmentClassStudentResponseDTO> enrollments;
}
