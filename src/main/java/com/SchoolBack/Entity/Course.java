package com.SchoolBack.Entity;

import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.util.List;
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
@Table(name = "course")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable =  false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100,	unique = true)
	private String courseCode;
	
	@JsonIgnore
	@Column(name = "active")
	private boolean isActive;
	
	@OneToMany(mappedBy = "course",cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<Class> classes;
	
	@ManyToOne 
	@JoinColumn(name = "id_school")
	private School school;
}