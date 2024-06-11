package com.wpa.mm.student_registration.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "name is required")
	private String name;
	
	@NotBlank(message = "nrc is required")
	private String nrc;
	
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@Min(value = 9,message = "phonenumber must be 9 digits at least")
	private Integer phonenumber;
	
	@NotNull(message = "address must not be null")
	private String address;
	
	@NotNull(message = "dob must not be null")
	private LocalDate dob;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Enumerated(EnumType.STRING)
	private Hobby hobby;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "student",fetch = FetchType.LAZY)
	List<Report> reports = new ArrayList<>();
	
	@PrePersist
	void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

}
