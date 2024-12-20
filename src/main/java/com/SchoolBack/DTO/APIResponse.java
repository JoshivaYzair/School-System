package com.SchoolBack.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL )
@Builder
public class APIResponse<T> {
	
	private String status; 
	private List<ErrorDTO> errors;
	private T results;
	private Integer Page;
	private Integer PageSize;
	private Long totalElements;
	private String sortBy;
	private String sortDirection;
}
