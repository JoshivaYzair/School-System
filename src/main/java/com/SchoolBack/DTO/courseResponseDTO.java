package com.SchoolBack.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class courseResponseDTO {
	
	private Long id;
	private String name;
	private String courseCode;
	private int totalClases;
	private Set<classResponseDTO> classes;
}
