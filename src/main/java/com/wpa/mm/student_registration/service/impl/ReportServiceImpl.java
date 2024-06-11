package com.wpa.mm.student_registration.service.impl;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.repository.ReportsRepository;
import com.wpa.mm.student_registration.repository.StudentRepository;
import com.wpa.mm.student_registration.service.ReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
	
	private final ReportsRepository reportRepository;
	private final StudentRepository studentRepository;

	@Override
	public void createReport(Report report) {
		// TODO Auto-generated method stub
		if(report == null) {
			throw new NullPointerException("report is null");
		}
		reportRepository.save(report);
	}

	@Override
	public List<Report> getReportsByStudent(Long studentId) throws FileNotFoundException {
		// TODO Auto-generated method stub
		Optional<Student> studentOpt = studentRepository.findById(studentId);
		if(studentOpt.isEmpty()) {
			throw new FileNotFoundException("Student with id "+studentId+" is not found.");
		}
		return reportRepository.findByStudent(studentOpt.get());
	}
	
	@Override
	public boolean deleteReportById(Long id) {
		// TODO Auto-generated method stub
		Optional<Report> reportOpt = reportRepository.findById(id);
		if(reportOpt.isEmpty()) {
			System.out.println("report id "+id+" is not found.");
			return false;
		}
		reportOpt.get().setStudent(null);
		reportRepository.save(reportOpt.get());
		reportRepository.deleteById(id);
		return true;
	}

}
