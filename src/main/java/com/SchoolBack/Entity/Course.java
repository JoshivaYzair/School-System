package com.SchoolBack.Entity;

import com.SchoolBack.Entity.Interface.Activable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "course")
public class Course implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable =  false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String courseCode;
	
	@Column (name = "schedule", nullable = false)
	private String schedule;
	
	@Column(name = "active")
	private boolean isActive;
	
	@ManyToOne
	@JoinColumn(name = "id_teacher", nullable = false, referencedColumnName = "name", insertable = false, updatable = false)
	private Teacher teacher;
	
	@ManyToOne
	@JoinColumn(name = "id_school", nullable = false, referencedColumnName = "school_title", insertable = false, updatable = false)
	private School school;
	
	@OneToMany(mappedBy = "course")
	private List<Grade> grades;
	
	@ManyToMany(mappedBy = "courses")
	private List<Student> students;
}
