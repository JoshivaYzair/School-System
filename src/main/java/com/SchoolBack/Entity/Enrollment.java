package com.SchoolBack.Entity;

import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "enrollment", uniqueConstraints = {
	@jakarta.persistence.UniqueConstraint(name = "unique_student_class", columnNames = {"id_student", "id_class"})
})
public class Enrollment implements Activable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_student")
	@JsonBackReference
	private Student student;

	@ManyToOne
	@JoinColumn(name = "id_class")
	@JsonBackReference
	private Class aClass;

	@Column(name = "status")
	private String status;

	@JsonIgnore
	@Column(name = "active")
	private boolean isActive;
}
