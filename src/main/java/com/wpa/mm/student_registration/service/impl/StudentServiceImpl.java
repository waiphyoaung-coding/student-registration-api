package com.wpa.mm.student_registration.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.repository.ReportsRepository;
import com.wpa.mm.student_registration.repository.StudentRepository;
import com.wpa.mm.student_registration.service.StudentService;
import com.wpa.mm.student_registration.util.ExcelHelper;

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
	
	public void save(MultipartFile file) {
        try {
            List<Student> students = ExcelHelper.excelToStudents(file.getInputStream());
            studentRepository.saveAll(students);
            List<Report> reports = ExcelHelper.excelToReports(file.getInputStream());
            reportsRepository.saveAll(reports);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream load() {
        List<Student> students = (List<Student>) studentRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");
            Sheet sheet2 = workbook.createSheet("Reports");

            Row headerRow = sheet.createRow(0);

            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("NRC");
            headerRow.createCell(3).setCellValue("DateOfBirth");
            headerRow.createCell(4).setCellValue("Gender");
            headerRow.createCell(5).setCellValue("State");
            headerRow.createCell(6).setCellValue("Phone");
            headerRow.createCell(7).setCellValue("Email");
            headerRow.createCell(8).setCellValue("Address");
            headerRow.createCell(9).setCellValue("Hobby");

            int rowIdx = 1;
            for (Student student : students) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(student.getStudentID());
                row.createCell(1).setCellValue(student.getName());
                row.createCell(2).setCellValue(student.getNrc());
                row.createCell(3).setCellValue(student.getDob());
                row.createCell(4).setCellValue(student.getGender().toString());
                row.createCell(5).setCellValue(student.getState().toString());
                row.createCell(6).setCellValue(student.getPhonenumber());
                row.createCell(7).setCellValue(student.getEmail());
                row.createCell(8).setCellValue(student.getAddress());
                row.createCell(9).setCellValue(String.join(",", student.getHobby()));
            }
            
            Row reportHeader = sheet2.createRow(0);
            
            reportHeader.createCell(0).setCellValue("StudentID");
            reportHeader.createCell(1).setCellValue("Year");
            reportHeader.createCell(2).setCellValue("Myanmar");
            reportHeader.createCell(3).setCellValue("English");
            reportHeader.createCell(4).setCellValue("Mathematic");
            reportHeader.createCell(5).setCellValue("History");
            reportHeader.createCell(6).setCellValue("Science");
            reportHeader.createCell(7).setCellValue("Total");
            
            int rowIndx = 1;
            for (Report report : reportsRepository.findAll()) {
				Row row = sheet2.createRow(rowIndx++);
				
				row.createCell(0).setCellValue(report.getStudent().getStudentID());
				row.createCell(1).setCellValue(report.getAcademicYear());
				row.createCell(2).setCellValue(report.getMyanmar());
				row.createCell(3).setCellValue(report.getEnglish());
				row.createCell(4).setCellValue(report.getMathematic());
				row.createCell(5).setCellValue(report.getHistory());
				row.createCell(6).setCellValue(report.getScience());
				row.createCell(7).setCellValue(report.getTotal());
			}

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }

	@Override
	public ByteArrayInputStream loadById(Long studentId) {
		// TODO Auto-generated method stub
		Optional<Student> studentOpt = studentRepository.findById(studentId);
		if(studentOpt.isEmpty()) {
			throw new NullPointerException("student with id:"+studentId+"is null.");
		}
		Student student = studentOpt.get();
		try(Workbook workbook = new XSSFWorkbook();) {
			Sheet sheet1 = workbook.createSheet("Students");
			Sheet sheet2 = workbook.createSheet("Reports");
			
			Row headerRowStu = sheet1.createRow(0);
			headerRowStu.createCell(0).setCellValue("ID");
			headerRowStu.createCell(1).setCellValue("Name");
			headerRowStu.createCell(2).setCellValue("NRC");
			headerRowStu.createCell(3).setCellValue("DateOfBirth");
			headerRowStu.createCell(4).setCellValue("Gender");
			headerRowStu.createCell(5).setCellValue("State");
			headerRowStu.createCell(6).setCellValue("Phone");
			headerRowStu.createCell(7).setCellValue("Email");
			headerRowStu.createCell(8).setCellValue("Address");
			headerRowStu.createCell(9).setCellValue("Hobby");
			
			Row rowStu = sheet1.createRow(1);
			rowStu.createCell(0).setCellValue(student.getStudentID());
			rowStu.createCell(1).setCellValue(student.getName());
			rowStu.createCell(2).setCellValue(student.getNrc());
			rowStu.createCell(3).setCellValue(student.getDob());
			rowStu.createCell(4).setCellValue(student.getGender().toString());
			rowStu.createCell(5).setCellValue(student.getState().toString());
			rowStu.createCell(6).setCellValue(student.getPhonenumber());
			rowStu.createCell(7).setCellValue(student.getEmail());
			rowStu.createCell(8).setCellValue(student.getAddress());
			rowStu.createCell(9).setCellValue(String.join(",", student.getHobby()));
			
			Row reportHeader = sheet2.createRow(0);
            reportHeader.createCell(0).setCellValue("StudentID");
            reportHeader.createCell(1).setCellValue("Year");
            reportHeader.createCell(2).setCellValue("Myanmar");
            reportHeader.createCell(3).setCellValue("English");
            reportHeader.createCell(4).setCellValue("Mathematic");
            reportHeader.createCell(5).setCellValue("History");
            reportHeader.createCell(6).setCellValue("Science");
            reportHeader.createCell(7).setCellValue("Total");
            
            List<Report> reportList = reportsRepository.findByStudent(student);
            if(!reportList.isEmpty()) {
            	int rowIndx = 1;
                for (Report report : reportList) {
    				Row row = sheet2.createRow(rowIndx++);
    				
    				row.createCell(0).setCellValue(report.getStudent().getStudentID());
    				row.createCell(1).setCellValue(report.getAcademicYear());
    				row.createCell(2).setCellValue(report.getMyanmar());
    				row.createCell(3).setCellValue(report.getEnglish());
    				row.createCell(4).setCellValue(report.getMathematic());
    				row.createCell(5).setCellValue(report.getHistory());
    				row.createCell(6).setCellValue(report.getScience());
    				row.createCell(7).setCellValue(report.getTotal());
    			}
            }
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
	}

}
