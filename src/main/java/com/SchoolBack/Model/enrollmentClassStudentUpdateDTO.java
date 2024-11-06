package com.SchoolBack.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class enrollmentClassStudentUpdateDTO {

	private Long studentId;
	private Long classId;
	private boolean isActive;
	private String status;
}
