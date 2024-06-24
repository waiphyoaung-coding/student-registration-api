package com.wpa.mm.student_registration.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wpa.mm.student_registration.domain.Gender;
import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.State;
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
	
	@Override
	public List<Student> save(MultipartFile file) {
	    Logger logger = LoggerFactory.getLogger(this.getClass());
	    List<Student> studentList = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            
					Sheet sheet = workbook.getSheetAt(0);
					
					Iterator<Row> rows = sheet.iterator();
		            int rowNumber = 0;
		            while (rows.hasNext()) {
		                Row currentRow = rows.next();

		                // skip header
		                if (rowNumber == 0) {
		                    rowNumber++;
		                    continue;
		                }

		                Iterator<Cell> cellsInRow = currentRow.iterator();

		                Student student = new Student();
		                Report report = new Report();

		                int cellIdx = 0;
		                while (cellsInRow.hasNext()) {
		                    Cell currentCell = cellsInRow.next();

		                    switch (cellIdx) {
		                        case 0:
		                            student.setStudentID(currentCell.getStringCellValue());
		                            break;
		                        case 1:
		                            student.setName(currentCell.getStringCellValue());
		                            break;
		                        case 2:
		                            student.setNrc(currentCell.getStringCellValue());
		                            break;
		                        case 3:
		                            student.setDob(currentCell.getLocalDateTimeCellValue().toLocalDate());
		                            break;
		                        case 4:
		                            student.setGender(Gender.valueOf(currentCell.getStringCellValue().toUpperCase()));
		                            break;
		                        case 5:
		                            student.setState(State.valueOf(currentCell.getStringCellValue().toUpperCase()));
		                            break;
		                        case 6:
		                            student.setPhonenumber((int) currentCell.getNumericCellValue());
		                            break;
		                        case 7:
		                            student.setEmail(currentCell.getStringCellValue());
		                            break;
		                        case 8:
		                            student.setAddress(currentCell.getStringCellValue());
		                            break;
		                        case 9:
		                        	String hobbies = currentCell.getStringCellValue();
		                        	String[] hobbyList = hobbies.split(",");
		                        	student.setHobby(Arrays.asList(hobbyList));
		                            break;
		                        case 10:
		                        	report.setMyanmar((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 11:
		                        	report.setEnglish((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 12:
		                        	report.setMathematic((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 13:
		                        	report.setHistory((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 14:
		                        	report.setScience((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 15:
		                        	report.setTotal((int) currentCell.getNumericCellValue());
		                        	break;
		                        case 16:
		                        	report.setAcademicYear((int) currentCell.getNumericCellValue());
		                        	break;
		                        default:
		                            break;
		                    }

		                    cellIdx++;
		                }
		                if (student.getStudentID() == null || student.getStudentID().isEmpty() ||
		                        student.getName() == null || student.getName().isEmpty() ||
		                        student.getNrc() == null || student.getNrc().isEmpty() ||
		                        student.getDob() == null ||
		                        student.getAddress() == null || student.getAddress().isEmpty()) {
		                        logger.error("Missing required field in row {}: {}", rowNumber, currentRow);
		                        continue; // skip this record
		                    }
		                
		               Optional<Student> studentOpt = studentRepository.findByStudentID(student.getStudentID());
		               if(report.getAcademicYear() != null) {
		            	   if(studentOpt.isEmpty()) {
				               Student newStudent = studentRepository.save(student);
				               report.setStudent(newStudent);
				               studentList.add(newStudent);
			               }else {
			            	   report.setStudent(studentOpt.get());
			            	   studentList.add(student);
			               }
			               Report newReport = reportsRepository.save(report);
			               student.getReports().add(newReport);
		               }else {
		            	   if(studentOpt.isEmpty()) {
				               studentRepository.save(student);
				               studentList.add(student);
			               }
		               }
		            }

            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
		return studentList;
    }

    public ByteArrayInputStream load() {
        List<Student> students = (List<Student>) studentRepository.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");
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

            headerRow.createCell(10).setCellValue("Myanmar");
            headerRow.createCell(11).setCellValue("English");
            headerRow.createCell(12).setCellValue("Mathematic");
            headerRow.createCell(13).setCellValue("History");
            headerRow.createCell(14).setCellValue("Science");
            headerRow.createCell(15).setCellValue("Total");
            headerRow.createCell(16).setCellValue("Year");
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            short dateFormat = creationHelper.createDataFormat().getFormat("yyyy-MM-dd");
            dateCellStyle.setDataFormat(dateFormat);
            
            int rowIdx = 1;
            for (Student student : students) {
            	List<Report> reportList = reportsRepository.findByStudent(student);
            	
            	if(!reportList.isEmpty()) {

                    for (Report report : reportList) {
                        Row row = sheet.createRow(rowIdx++);
	                    row.createCell(0).setCellValue(student.getStudentID());
	                    row.createCell(1).setCellValue(student.getName());
	                    row.createCell(2).setCellValue(student.getNrc());
	                    
	                    Cell cell = row.createCell(3);
	                    Date date = Date.from(student.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant());
	                    cell.setCellValue(date);
	                    cell.setCellStyle(dateCellStyle);
	                    
	                    row.createCell(4).setCellValue(student.getGender().toString());
	                    row.createCell(5).setCellValue(student.getState().toString());
	                    row.createCell(6).setCellValue(student.getPhonenumber());
	                    row.createCell(7).setCellValue(student.getEmail());
	                    row.createCell(8).setCellValue(student.getAddress());
	                    row.createCell(9).setCellValue(String.join(",", student.getHobby()));
	                    
	    				row.createCell(10).setCellValue(report.getMyanmar());
	    				row.createCell(11).setCellValue(report.getEnglish());
	    				row.createCell(12).setCellValue(report.getMathematic());
	    				row.createCell(13).setCellValue(report.getHistory());
	    				row.createCell(14).setCellValue(report.getScience());
	    				row.createCell(15).setCellValue(report.getTotal());
	    				row.createCell(16).setCellValue(report.getAcademicYear());
					}
                    
            	}else {
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
			Sheet sheet = workbook.createSheet("Students");
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

            headerRow.createCell(10).setCellValue("Myanmar");
            headerRow.createCell(11).setCellValue("English");
            headerRow.createCell(12).setCellValue("Mathematic");
            headerRow.createCell(13).setCellValue("History");
            headerRow.createCell(14).setCellValue("Science");
            headerRow.createCell(15).setCellValue("Total");
            headerRow.createCell(16).setCellValue("Year");
            
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle dateCellStyle = workbook.createCellStyle();
            short dateFormat = creationHelper.createDataFormat().getFormat("yyyy-MM-dd");
            dateCellStyle.setDataFormat(dateFormat);

            List<Report> reportList = reportsRepository.findByStudent(student);
            int rowIdx = 1;
        	if(!reportList.isEmpty()) {

                for (Report report : reportList) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(student.getStudentID());
                    row.createCell(1).setCellValue(student.getName());
                    row.createCell(2).setCellValue(student.getNrc());
                    
                    Cell cell = row.createCell(3);
                    cell.setCellValue(student.getDob());
                    cell.setCellStyle(dateCellStyle);
                    
                    row.createCell(4).setCellValue(student.getGender().toString());
                    row.createCell(5).setCellValue(student.getState().toString());
                    row.createCell(6).setCellValue(student.getPhonenumber());
                    row.createCell(7).setCellValue(student.getEmail());
                    row.createCell(8).setCellValue(student.getAddress());
                    row.createCell(9).setCellValue(String.join(",", student.getHobby()));
                    
    				row.createCell(10).setCellValue(report.getMyanmar());
    				row.createCell(11).setCellValue(report.getEnglish());
    				row.createCell(12).setCellValue(report.getMathematic());
    				row.createCell(13).setCellValue(report.getHistory());
    				row.createCell(14).setCellValue(report.getScience());
    				row.createCell(15).setCellValue(report.getTotal());
    				row.createCell(16).setCellValue(report.getAcademicYear());
				}
                
        	}else {
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
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
	}

	@Override
	public ByteArrayInputStream generateFile() {
		// TODO Auto-generated method stub
		try(Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Students");
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

            headerRow.createCell(10).setCellValue("Myanmar");
            headerRow.createCell(11).setCellValue("English");
            headerRow.createCell(12).setCellValue("Mathematic");
            headerRow.createCell(13).setCellValue("History");
            headerRow.createCell(14).setCellValue("Science");
            headerRow.createCell(15).setCellValue("Total");
            headerRow.createCell(16).setCellValue("Year");
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
	}


}
