package com.SchoolBack.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class registerUser {
	private String name;
	@Email(message = "INVALID email address.")
	private String email;
	@NotBlank(message = "password shouldn't be EMPTY.")
	@NotNull(message = "password shouldn't be NULL.")
	private String password;
	private String majorOrDepartament;
	private String typeUser;
}
