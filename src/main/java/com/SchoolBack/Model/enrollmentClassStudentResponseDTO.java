package com.SchoolBack.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class enrollmentClassStudentResponseDTO {

	private Long id;
	private studentResponseDTO student;
	private classResponseDTO aClass;
	private String status;
}
