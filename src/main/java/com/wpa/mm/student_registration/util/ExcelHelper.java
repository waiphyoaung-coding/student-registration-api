package com.wpa.mm.student_registration.util;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.wpa.mm.student_registration.domain.Gender;
import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.State;
import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.repository.StudentRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Component
public class ExcelHelper {
	
	private static StudentRepository studentRepository;
	
	public ExcelHelper(StudentRepository studentRepository) {
        ExcelHelper.studentRepository = studentRepository;
    }

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public static boolean hasExcelFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<Student> excelToStudents(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            int numberOfSheets = workbook.getNumberOfSheets();
            List<Student> students = new ArrayList<>();
            
            for (int i = 0; i < numberOfSheets; i++) {
				if(workbook.getSheetName(i).toLowerCase().equals("students")) {
					Sheet sheet = workbook.getSheetAt(i);
					
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
		                            student.setGender(Gender.valueOf(currentCell.getStringCellValue()));
		                            break;
		                        case 5:
		                            student.setState(State.valueOf(currentCell.getStringCellValue()));
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
		                        default:
		                            break;
		                    }

		                    cellIdx++;
		                }

		                students.add(student);
		            }
				}
			}

            workbook.close();

            return students;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
    
    public static List<Report> excelToReports(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            int numberOfSheets = workbook.getNumberOfSheets();
            List<Report> reports = new ArrayList<>();
            
            for (int i = 0; i < numberOfSheets; i++) {
				if(workbook.getSheetName(i).toLowerCase().equals("reports")) {
					Sheet sheet = workbook.getSheetAt(i);
					
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

		                Report report = new Report();

		                int cellIdx = 0;
		                while (cellsInRow.hasNext()) {
		                    Cell currentCell = cellsInRow.next();
		                    switch (cellIdx) {
			                    case 0:
		                            Optional<Student> stuOpt = studentRepository.findByStudentID(currentCell.getStringCellValue());
		                            if(stuOpt.isPresent()) {
		                            	report.setStudent(stuOpt.get());
		                            }
		                            break;
		                        case 1:
		                            report.setAcademicYear((int) currentCell.getNumericCellValue());
		                            break;
		                        case 2:
		                            report.setMyanmar((int) currentCell.getNumericCellValue());
		                            break;
		                        case 3:
		                            report.setEnglish((int) currentCell.getNumericCellValue());
		                            break;
		                        case 4:
		                            report.setMathematic((int) currentCell.getNumericCellValue());
		                            break;
		                        case 5:
		                            report.setHistory((int) currentCell.getNumericCellValue());
		                            break;
		                        case 6:
		                            report.setScience((int) currentCell.getNumericCellValue());
		                            break;
		                        case 7:
		                            report.setTotal((int) currentCell.getNumericCellValue());
		                            break;
		                        default:
		                            break;
		                    }
		                    
		                    cellIdx++;
		                }
		                reports.add(report);
		            }
				}
			}

            workbook.close();

            return reports;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}

