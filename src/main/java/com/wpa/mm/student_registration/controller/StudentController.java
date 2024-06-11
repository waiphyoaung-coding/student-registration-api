package com.wpa.mm.student_registration.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.service.MapValidationService;
import com.wpa.mm.student_registration.service.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

	private final StudentService studentService;
	private final MapValidationService mapValidationService;
	
	@GetMapping("/fetch")
	public ResponseEntity<List<Student>> getAllStudents(){
		return new ResponseEntity<List<Student>>(studentService.getAllStudents(), HttpStatus.OK);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long studentId){
		Optional<Student> studentOpt = studentService.getStudentById(studentId);
		if(studentOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Student>(studentOpt.get(), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createStudent(@Valid @RequestBody Student student,BindingResult result){
		if(result.hasErrors()) {
			return mapValidationService.validate(result);
		}
		return new ResponseEntity<Student>(studentService.createStudent(student), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateStudent(@Valid @RequestBody Student student,BindingResult result){
		if(result.hasErrors()) {
			return mapValidationService.validate(result);
		}
		return new ResponseEntity<Student>(studentService.updateStudent(student), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{studentId}")
	public ResponseEntity<Long> deleteStudent(@PathVariable Long studentId){
		boolean success = studentService.deleteStudentById(studentId);
		if(!success) {
			return new ResponseEntity<Long>(studentId, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Long>(studentId, HttpStatus.OK);
	}
	
}
