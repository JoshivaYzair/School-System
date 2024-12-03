package com.SchoolBack.DTO.Request.Student;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class studentUpdateDTO {
	private String name;
	private String major;
}
