package com.SchoolBack.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class enrollmentUpdateDTO {

	private Long studentId;
	private Long classId;
	private boolean isActive;
	private String status;
}
