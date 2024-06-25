package com.security.SpringSecurity.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.SpringSecurity.Entity.Interface.Activable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	@JoinColumn(name = "user_email", referencedColumnName = "email", insertable = false, updatable = false)
	private User user;
	
	@OneToMany (mappedBy = "teacher")
	@JsonIgnore
	private List<Course> courses;
	
}
