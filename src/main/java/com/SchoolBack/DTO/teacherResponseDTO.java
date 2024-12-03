package com.SchoolBack.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.SchoolBack.Entity.Class;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class teacherResponseDTO {
	
	private Long id;
	private String name;
	private String email;
	private String departament;
	private Set<classResponseDTO> classList;
}
