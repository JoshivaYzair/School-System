package com.security.SpringSecurity.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.security.SpringSecurity.Entity.Interface.Activable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "school")
public class School implements Activable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String school_title;
	
	@Column (nullable = false, length = 200)
	private String address;
	
	@Column(name = "active")
	private boolean isActive;
	
//	@ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnore
	@OneToMany(mappedBy = "school")
	private List<Course> courses;
	
}
