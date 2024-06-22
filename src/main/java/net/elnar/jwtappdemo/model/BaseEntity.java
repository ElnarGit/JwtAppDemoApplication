package net.elnar.jwtappdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@CreatedDate
	@Column(name = "created")
	private Date created;
	
	@LastModifiedDate
	@Column(name = "updated")
	private Date updated;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;
}
