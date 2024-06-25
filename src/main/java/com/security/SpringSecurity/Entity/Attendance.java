package com.security.SpringSecurity.Entity;

import com.security.SpringSecurity.Entity.Interface.Activable;
import com.security.SpringSecurity.Enum.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "attendance")
public class Attendance implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "date")
	private LocalDate date;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Column(name = "active")
	private boolean isActive;
	
	@ManyToOne
	@JoinColumn (name = "id_student", nullable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn (name = "id_course", nullable = false)
	private Course course;
}
