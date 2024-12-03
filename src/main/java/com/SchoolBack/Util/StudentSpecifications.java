package com.SchoolBack.Util;

import com.SchoolBack.Entity.Enrollment;
import com.SchoolBack.Entity.Student;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecifications {

	public static Specification<Student> notEnrolledInClass(Long classId) {
		return (root, query, criteriaBuilder) -> {
			// Crear una subconsulta para encontrar estudiantes que están en la clase
			Subquery<Long> subquery = query.subquery(Long.class);
			Root<Enrollment> enrollment = subquery.from(Enrollment.class);

			// Seleccionar los IDs de los estudiantes que están inscritos en la clase especificada
			subquery.select(enrollment.get("student").get("id"))
			.where(criteriaBuilder.equal(enrollment.get("aClass").get("id"), classId));

			// Retornar solo los estudiantes cuyo ID no esté en la lista de la subconsulta
			return criteriaBuilder.not(root.get("id").in(subquery));
		};

//		return (root, query, builder) -> {
//			// Realizamos un LEFT JOIN con la entidad `Enrollment` en la propiedad `enrollments` de `Student`
//			Join<Object, Object> enrollmentJoin = root.join("enrollments", JoinType.LEFT);
//
//			// Creamos una condición para encontrar estudiantes donde la clase en la que están inscritos no sea `idClass` o la inscripción no exista (null)
//			Predicate classNotMatched = builder.or(
//			builder.notEqual(enrollmentJoin.get("aClass").get("id"), classId),
//			builder.isNull(enrollmentJoin.get("aClass"))
//			);
//
//			// Aplicamos la condición
//			return classNotMatched;
//		};
	}
}
