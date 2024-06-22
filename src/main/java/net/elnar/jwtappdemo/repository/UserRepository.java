package net.elnar.jwtappdemo.repository;

import net.elnar.jwtappdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String name);
}
