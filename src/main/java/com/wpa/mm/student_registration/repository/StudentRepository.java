package com.wpa.mm.student_registration.repository;

import org.springframework.data.repository.CrudRepository;

import com.wpa.mm.student_registration.domain.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {

}
