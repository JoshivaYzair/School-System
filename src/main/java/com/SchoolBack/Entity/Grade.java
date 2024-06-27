package com.SchoolBack.Entity;

import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "grade")
public class Grade implements Activable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "grade")
	private int grade;
	
	@Column (name = "date")
	private LocalDate date;
	
	@Column(name = "active")
	private boolean isActive;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "id_student", nullable = false)
	private Student student;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn (name = "id_course", nullable = false)
	private Course course;
}
