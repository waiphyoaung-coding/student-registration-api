package com.wpa.mm.student_registration.service;

import java.io.FileNotFoundException;
import java.util.List;

import com.wpa.mm.student_registration.domain.Report;

public interface ReportService {
	
	void createReport(Report report);
	
	List<Report> getReportsByStudent(Long studentId) throws FileNotFoundException;
	
	boolean deleteReportById(Long id);

}
