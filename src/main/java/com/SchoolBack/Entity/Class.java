package com.SchoolBack.Entity;

import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "class")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class Class implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "name")
	private String name;
	
	@Column (name = "schedule")
	private String schedule;
	
	@ManyToOne
	@JoinColumn(name = "id_course")
	@JsonBackReference
	private Course course;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_teacher")
	@JsonBackReference
	private Teacher teacher;
	
	@JsonBackReference 
	@OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Enrollment> enrollments;
	
	@JsonIgnore
	@Column(name = "active")
	private boolean isActive;
}
