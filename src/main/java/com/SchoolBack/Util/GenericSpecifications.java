package com.SchoolBack.Util;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecifications {

	public static <T> Specification<T> isActive() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isActive"));
	}
}
