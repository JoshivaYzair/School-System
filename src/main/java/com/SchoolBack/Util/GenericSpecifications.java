package com.SchoolBack.Util;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecifications {

	public static <T> Specification<T> isActive() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(root.get("isActive"));
	}

	public static <T> Specification<T> hasNameOrEmail(String searchTerm) {
		return (root, query, criteriaBuilder) -> {
			if (searchTerm == null || searchTerm.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			boolean isEmail = searchTerm.contains("@");
			if (isEmail) {
				return criteriaBuilder.like(criteriaBuilder.lower(root.join("user").get("email")),
				"%" + searchTerm.toLowerCase() + "%");
			} else {
				return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
				"%" + searchTerm.toLowerCase() + "%");
			}
		};
	}

	public static <T> Specification<T> hasFieldInList(String fieldName, List<String> values) {
		return (root, query, criteriaBuilder) -> {
			if (values == null || values.isEmpty()) {
				return criteriaBuilder.conjunction(); 
			}
			return root.get(fieldName).in(values);
		};
	}

}
