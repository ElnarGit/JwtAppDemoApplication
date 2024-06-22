package net.elnar.jwtappdemo.config;

import lombok.RequiredArgsConstructor;
import net.elnar.jwtappdemo.security.jwt.JwtConfigurer;
import net.elnar.jwtappdemo.security.jwt.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtTokenProvider jwtTokenProvider;
	
	private static final String ADMIN_ENDPOINT = "/api/v1/admin/**";
	private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
								.requestMatchers(LOGIN_ENDPOINT).permitAll()
								.requestMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
								.anyRequest().authenticated()
				)
				.apply(new JwtConfigurer(jwtTokenProvider));
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
