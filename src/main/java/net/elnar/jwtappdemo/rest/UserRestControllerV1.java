package net.elnar.jwtappdemo.rest;

import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class UserRestControllerV1 {
	private final UserService userService;
	
	@GetMapping("{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
		User user = userService.findById(id);
		
		if(user == null){
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		UserDto result = UserDto.fromUser(user);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
}
