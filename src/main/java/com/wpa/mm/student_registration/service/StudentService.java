package com.wpa.mm.student_registration.service;

import java.util.List;
import java.util.Optional;

import com.wpa.mm.student_registration.domain.Student;

public interface StudentService {
	
	Student createStudent(Student student);
	
	Optional<Student> getStudentById(Long id);
	
	List<Student> getAllStudents();
	
	Student updateStudent(Student student);
	
	boolean deleteStudentById(Long id);
}
