package com.SchoolBack.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.SchoolBack.Entity.Interface.Activable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "teacher")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Teacher implements Activable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column (nullable = false)
	private String name;
	
	@JsonIgnore
	@Column(name = "active")
	private boolean isActive;
	
	@Column (nullable = false, length = 100)
	private String departament;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_email", referencedColumnName = "email", unique = true)
	private User user;
	
	@JsonManagedReference
	@OneToMany (mappedBy = "teacher",fetch = FetchType.LAZY)
	private Set<Class> classes;
	
}
