package com.SchoolBack.Model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagedResponseDTO<T> {

	private List<T> content;
	private int Page;
	private int PageSize;
	private long totalElements;
	private String sortBy;
	private String sortDirection;

}
