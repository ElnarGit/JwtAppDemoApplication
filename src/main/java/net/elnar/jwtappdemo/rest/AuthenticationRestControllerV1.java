package net.elnar.jwtappdemo.rest;

import lombok.RequiredArgsConstructor;
import net.elnar.jwtappdemo.dto.AuthenticationRequestDto;
import net.elnar.jwtappdemo.model.User;
import net.elnar.jwtappdemo.security.jwt.JwtTokenProvider;
import net.elnar.jwtappdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationRestControllerV1 {
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;
	
	@PostMapping("login")
	public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
		try {
			String username = requestDto.getUsername();
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
			User user = userService.findByUsername(username);
			
			if(user == null){
				throw new UsernameNotFoundException("User with username: " + username + " not found");
			}
			
			String token = jwtTokenProvider.createToken(username, user.getRoles());
			
			Map<Object, Object> response = new HashMap<>();
			response.put("username", username);
			response.put("token", token);
			
			return ResponseEntity.ok(response);
		}catch (AuthenticationException e){
			throw new BadCredentialsException("Invalid username or password");
		}
	}
}
