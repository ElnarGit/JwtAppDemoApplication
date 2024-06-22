package net.elnar.jwtappdemo.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.elnar.jwtappdemo.model.User;
import net.elnar.jwtappdemo.security.jwt.JwtUser;
import net.elnar.jwtappdemo.security.jwt.JwtUserFactory;
import net.elnar.jwtappdemo.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
	
	private final UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		
		if(user == null){
			throw new UsernameNotFoundException("User with username: " + username + " not found");
		}
		
		JwtUser jwtUser = JwtUserFactory.create(user);
		log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
		return jwtUser;
	}
}
