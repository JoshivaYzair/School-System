package com.SchoolBack.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student")
public class Student implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(length = 100)
	private String major;
	
	@Column(name = "active")
	private boolean isActive;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_school")
	private School school;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_email", referencedColumnName = "email",unique = true)
	private User user;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "student" ,fetch = FetchType.LAZY) 
	private List<Enrollment> enrollments;
	
}
