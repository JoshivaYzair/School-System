package com.SchoolBack.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column (nullable = false)
	private String name;
	
	@Column(name = "active")
	private boolean isActive;
	
	@Column (nullable = false, length = 100)
	private String departament;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_email", referencedColumnName = "email")
	private User user;
	
	@OneToMany (mappedBy = "teacher",fetch = FetchType.LAZY)
	private List<Course> courses;
	
}
