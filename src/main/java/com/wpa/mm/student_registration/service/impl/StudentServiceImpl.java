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

	@Override
	public Student createStudent(Student student) {
		// TODO Auto-generated method stub
		if(student == null) {
			throw new NullPointerException("student is null");
		}
		Student newStudent = studentRepository.save(student);
		List<Report> reportList = student.getReports();
		
		if(!reportList.isEmpty()) {
			List<Report> savedList = (List<Report>) reportsRepository.saveAll(reportList);
			for (Report report : savedList) {
				report.setStudent(newStudent);
				reportsRepository.save(report);
			}
		}
		return newStudent;
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
		
		if(!student.getReports().isEmpty()) {
			for (Report report : student.getReports()) {
				report.setStudent(null);
				reportsRepository.delete(report);
			}
		}
		student.getReports().clear();
		List<Report> newReportList = new ArrayList<>();
		if(!newStudent.getReports().isEmpty()) {
			for (Report report : newStudent.getReports()) {
				report.setStudent(student);
				newReportList.add(reportsRepository.save(report));
			}
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
		Optional<Student> studentOpt = studentRepository.findById(id);
		if(studentOpt.isEmpty()) {
			System.out.println("student id "+id+" is not found.");
			return false;
		}
		List<Report> reportList = reportsRepository.findByStudent(studentOpt.get());
		if(!reportList.isEmpty()) {
			reportsRepository.deleteAll(reportList);
		}
		studentRepository.deleteById(id);
		return true;
	}

}
