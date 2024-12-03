package com.SchoolBack.DTO.Request.Class;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class classUpdateDTO {
	private String name;
	private String schedule;
	private Long course;
	private Long teacher;
}
