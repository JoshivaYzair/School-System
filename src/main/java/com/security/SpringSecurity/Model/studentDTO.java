package com.security.SpringSecurity.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.security.SpringSecurity.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class studentDTO {
	@Email(message = "INVALID email address")
	private String email;
	@NotBlank(message = "password shouldn't be EMPTY")
	@NotNull(message = "password shouldn't be NULL")
	private String password;
	@NotBlank(message = "name shouldn't be EMPTY")
	private String name;
	private int grade;
	private Long schoolId;
}
