package net.elnar.jwtappdemo.rest;

import lombok.RequiredArgsConstructor;
import net.elnar.jwtappdemo.dto.AdminUserDto;
import net.elnar.jwtappdemo.dto.UserDto;
import net.elnar.jwtappdemo.model.User;
import net.elnar.jwtappdemo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminRestControllerV1 {
	private final UserService userService;
	
	@GetMapping("users/{id}")
	public ResponseEntity<AdminUserDto> getUserById(@PathVariable("id") Long id){
		User user = userService.findById(id);
		
		if(user == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		AdminUserDto result = AdminUserDto.fromUser(user);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
}
