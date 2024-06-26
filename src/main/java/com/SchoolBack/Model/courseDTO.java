package com.SchoolBack.Model;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class courseDTO {
	
	private String name;
	
	private String courseCode;
	
	private String schedule;
	
	@NonNull
	private Long teacherId;
	
	@NonNull
	private Long schoolId;

}
