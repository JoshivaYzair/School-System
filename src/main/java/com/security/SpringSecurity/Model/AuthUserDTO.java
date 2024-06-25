
package com.security.SpringSecurity.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthUserDTO {
	@Email(message = "INVALID email address")
	private String email;
	@NotBlank(message = "password shouldn't be EMPTY")
	@NotNull(message = "password shouldn't be NULL")
	private String password;
}
