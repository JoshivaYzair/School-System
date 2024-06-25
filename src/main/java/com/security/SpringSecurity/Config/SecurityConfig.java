
package com.security.SpringSecurity.Config;

import com.security.SpringSecurity.Enum.Permission;
import com.security.SpringSecurity.Enum.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	
	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthenticationFilter authFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(authRequest -> {
				authRequest.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
				//Student's Route
				.requestMatchers( "/api/v1/student/").hasAnyRole(Role.STUDENT.name(),Role.ADMIN.name(), Role.TEACHER.name())
				
				.requestMatchers(HttpMethod.POST, "/api/v1/student/**").hasAuthority(Permission.ADMIN_CREATE.getPermission())
				.requestMatchers(HttpMethod.DELETE, "/api/v1/student/**").hasAuthority(Permission.ADMIN_DELETE.getPermission())
				.requestMatchers(HttpMethod.PUT, "/api/v1/student/**").hasAnyAuthority(Permission.STUDENT_UPDATE.getPermission(),
																			Permission.ADMIN_UPDATE.getPermission())
				.requestMatchers(HttpMethod.GET, "/api/v1/student/**").hasAnyAuthority(Permission.STUDENT_READ.getPermission(),
																			Permission.TEACHER_READ.getPermission())
				
				//Teacher's Route
				.requestMatchers( "/api/v1/teacher/").hasAnyRole(Role.TEACHER.name(),Role.STUDENT.name(), Role.ADMIN.name())
			
				.requestMatchers(HttpMethod.POST, "/api/v1/teacher/**").hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
				.requestMatchers(HttpMethod.DELETE, "/api/v1/teacher/**").hasAnyAuthority(Permission.ADMIN_DELETE.getPermission())
				.requestMatchers(HttpMethod.PUT, "/api/v1/teacher/**").hasAnyAuthority(Permission.TEACHER_UPDATE.getPermission(),
																			Permission.ADMIN_UPDATE.getPermission())
				.requestMatchers(HttpMethod.GET, "/api/v1/teacher/**").hasAnyAuthority(Permission.TEACHER_READ.getPermission(),
																			Permission.STUDENT_READ.getPermission())
	
				//Course's Route
				.requestMatchers( "/api/v1/course/").hasAnyRole(Role.TEACHER.name(), Role.ADMIN.name(),Role.STUDENT.name())
			
				.requestMatchers(HttpMethod.POST, "/api/v1/course/**").hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
				.requestMatchers(HttpMethod.DELETE, "/api/v1/course/**").hasAnyAuthority(Permission.ADMIN_DELETE.getPermission())
				.requestMatchers(HttpMethod.PUT, "/api/v1/course/**").hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission())
				.requestMatchers(HttpMethod.GET, "/api/v1/course/**").hasAnyAuthority(Permission.TEACHER_READ.getPermission(),
																			Permission.STUDENT_READ.getPermission())
				.anyRequest().denyAll();
			})
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}