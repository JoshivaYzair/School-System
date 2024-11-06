package com.SchoolBack.Model;

import com.SchoolBack.Entity.Enrollment;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class studentResponseDTO {
	private long id;
	private String name;
	private String major;
	private String email;
	private Set  <enrollmentClassStudentResponseDTO> enrollment;
}

