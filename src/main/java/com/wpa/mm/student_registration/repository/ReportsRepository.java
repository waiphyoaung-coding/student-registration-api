package com.wpa.mm.student_registration.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.Student;

public interface ReportsRepository extends CrudRepository<Report, Long>{
	List<Report> findByStudent(Student student);
}
