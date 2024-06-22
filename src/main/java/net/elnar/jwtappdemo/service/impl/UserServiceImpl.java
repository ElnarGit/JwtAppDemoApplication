package net.elnar.jwtappdemo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.elnar.jwtappdemo.model.Role;
import net.elnar.jwtappdemo.model.Status;
import net.elnar.jwtappdemo.model.User;
import net.elnar.jwtappdemo.repository.RoleRepository;
import net.elnar.jwtappdemo.repository.UserRepository;
import net.elnar.jwtappdemo.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public User register(User user) {
		Role roleUser = roleRepository.findByName("ROLE_USER");
		List<Role> userRoles = new ArrayList<>();
		userRoles.add(roleUser);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(userRoles);
		user.setStatus(Status.ACTIVE);
		
		User registeredUser = userRepository.save(user);
		
		log.info("IN register - user: {} successfully registered", registeredUser);
		
		return registeredUser;
	}
	
	@Override
	public List<User> getAll() {
		List<User> users = userRepository.findAll();
		log.info("IN getAll - {} users found", users.size());
		return users;
	}
	
	@Override
	public User findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		log.info("IN findByUsername - user {}  found by username: {}", user, username);
		return user;
	}
	
	@Override
	public User findById(Long id) {
		User user = userRepository.findById(id).orElse(null);
		
		if(user == null){
			log.warn("IN findById - no user found by id: {}", id);
			return null;
		}
		log.info("IN findById - user {}  found by id: {}", user, id);
		return user;
	}
	
	@Override
	public void delete(Long id) {
		userRepository.deleteById(id);
		log.info("IN delete - user with id: {} successfully deleted", id);
	}
}
