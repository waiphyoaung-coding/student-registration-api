package com.wpa.mm.student_registration.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/report")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ReportController {
	
	private final ReportService reportService;
	
	@GetMapping("/fetch/{studentId}")
	public ResponseEntity<List<Report>> getReportsByStudent(@PathVariable Long studentId) throws FileNotFoundException{
		return new ResponseEntity<List<Report>>(reportService.getReportsByStudent(studentId), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{reportId}")
	public ResponseEntity<Long> deleteReport(@PathVariable Long reportId){
		boolean success = reportService.deleteReportById(reportId);
		if(!success) {
			return new ResponseEntity<Long>(reportId, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Long>(reportId, HttpStatus.OK);
	}

}
