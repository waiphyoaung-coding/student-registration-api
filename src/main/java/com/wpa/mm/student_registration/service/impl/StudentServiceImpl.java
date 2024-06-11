package com.wpa.mm.student_registration.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.repository.ReportsRepository;
import com.wpa.mm.student_registration.repository.StudentRepository;
import com.wpa.mm.student_registration.service.StudentService;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
	
	private final StudentRepository studentRepository;
	private final ReportsRepository reportsRepository;
	
	private Student save(Student student) {
		if(student == null) {
			throw new NullPointerException("student is null");
		}
		return studentRepository.save(student);
	}

	@Override
	public Student createStudent(Student student) {
		// TODO Auto-generated method stub
		return save(student);
	}

	@Override
	public Student updateStudent(Student newStudent) {
		// TODO Auto-generated method stub
		Optional<Student> studentOpt = studentRepository.findById(newStudent.getId());
		if(studentOpt.isEmpty()) {
			throw new NullPointerException();
		}
		Student student = studentOpt.get();
		student.setId(newStudent.getId());
		student.setName(newStudent.getName());
		student.setNrc(newStudent.getNrc());
		student.setEmail(newStudent.getEmail());
		student.setPhonenumber(newStudent.getPhonenumber());
		student.setAddress(newStudent.getAddress());
		student.setDob(newStudent.getDob());
		student.setGender(newStudent.getGender());
		student.setState(newStudent.getState());
		student.setHobby(newStudent.getHobby());
		
		student.getReports().clear();
		List<Report> newReportList = new ArrayList<>();
		for (Report report : newStudent.getReports()) {
			report.setStudent(student);
			newReportList.add(reportsRepository.save(report));
		}
		student.setReports(newReportList);
		return studentRepository.save(student);
	}

	@Override
	public Optional<Student> getStudentById(Long id) {
		// TODO Auto-generated method stub
		Optional<Student> studentOpt = studentRepository.findById(id);
		if(studentOpt.isEmpty()) {
			System.out.println("student id "+id+" is not found");
		}
		return studentOpt;
	}

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return (List<Student>) studentRepository.findAll();
	}

	@Override
	public boolean deleteStudentById(Long id) {
		// TODO Auto-generated method stub
		if(id == null) {
			System.out.println("student id is null");
			return false;
		}
		if(studentRepository.findById(id).isEmpty()) {
			System.out.println("student id "+id+" is not found.");
			return false;
		}
		return true;
	}

}
