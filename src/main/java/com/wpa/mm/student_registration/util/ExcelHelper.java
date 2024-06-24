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

//    public static List<Student> excelToStudents(InputStream is) {
//        try {
//            Workbook workbook = new XSSFWorkbook(is);
//            int numberOfSheets = workbook.getNumberOfSheets();
//            List<Student> students = new ArrayList<>();
//            
//            for (int i = 0; i < numberOfSheets; i++) {
//				if(workbook.getSheetName(i).toLowerCase().equals("students")) {
//					Sheet sheet = workbook.getSheetAt(i);
//					
//					Iterator<Row> rows = sheet.iterator();
//		            int rowNumber = 0;
//		            while (rows.hasNext()) {
//		                Row currentRow = rows.next();
//
//		                // skip header
//		                if (rowNumber == 0) {
//		                    rowNumber++;
//		                    continue;
//		                }
//
//		                Iterator<Cell> cellsInRow = currentRow.iterator();
//
//		                Student student = new Student();
//
//		                int cellIdx = 0;
//		                while (cellsInRow.hasNext()) {
//		                    Cell currentCell = cellsInRow.next();
//
//		                    switch (cellIdx) {
//		                        case 0:
//		                            student.setStudentID(currentCell.getStringCellValue());
//		                            break;
//		                        case 1:
//		                            student.setName(currentCell.getStringCellValue());
//		                            break;
//		                        case 2:
//		                            student.setNrc(currentCell.getStringCellValue());
//		                            break;
//		                        case 3:
//		                            student.setDob(currentCell.getLocalDateTimeCellValue().toLocalDate());
//		                            break;
//		                        case 4:
//		                            student.setGender(Gender.valueOf(currentCell.getStringCellValue()));
//		                            break;
//		                        case 5:
//		                            student.setState(State.valueOf(currentCell.getStringCellValue()));
//		                            break;
//		                        case 6:
//		                            student.setPhonenumber((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 7:
//		                            student.setEmail(currentCell.getStringCellValue());
//		                            break;
//		                        case 8:
//		                            student.setAddress(currentCell.getStringCellValue());
//		                            break;
//		                        case 9:
//		                        	String hobbies = currentCell.getStringCellValue();
//		                        	String[] hobbyList = hobbies.split(",");
//		                        	student.setHobby(Arrays.asList(hobbyList));
//		                            break;
//		                        default:
//		                            break;
//		                    }
//
//		                    cellIdx++;
//		                }
//
//		                students.add(student);
//		            }
//				}
//			}
//
//            workbook.close();
//
//            return students;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//    }
//    
//    public static List<Report> excelToReports(InputStream is) {
//        try {
//            Workbook workbook = new XSSFWorkbook(is);
//            int numberOfSheets = workbook.getNumberOfSheets();
//            List<Report> reports = new ArrayList<>();
//            
//            for (int i = 0; i < numberOfSheets; i++) {
//				if(workbook.getSheetName(i).toLowerCase().equals("reports")) {
//					Sheet sheet = workbook.getSheetAt(i);
//					
//					Iterator<Row> rows = sheet.iterator();
//		            int rowNumber = 0;
//		            while (rows.hasNext()) {
//		                Row currentRow = rows.next();
//
//		                // skip header
//		                if (rowNumber == 0) {
//		                    rowNumber++;
//		                    continue;
//		                }
//
//		                Iterator<Cell> cellsInRow = currentRow.iterator();
//
//		                Report report = new Report();
//
//		                int cellIdx = 0;
//		                while (cellsInRow.hasNext()) {
//		                    Cell currentCell = cellsInRow.next();
//		                    switch (cellIdx) {
//			                    case 0:
//		                            Optional<Student> stuOpt = studentRepository.findByStudentID(currentCell.getStringCellValue());
//		                            if(stuOpt.isPresent()) {
//		                            	report.setStudent(stuOpt.get());
//		                            }
//		                            break;
//		                        case 1:
//		                            report.setAcademicYear((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 2:
//		                            report.setMyanmar((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 3:
//		                            report.setEnglish((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 4:
//		                            report.setMathematic((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 5:
//		                            report.setHistory((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 6:
//		                            report.setScience((int) currentCell.getNumericCellValue());
//		                            break;
//		                        case 7:
//		                            report.setTotal((int) currentCell.getNumericCellValue());
//		                            break;
//		                        default:
//		                            break;
//		                    }
//		                    
//		                    cellIdx++;
//		                }
//		                reports.add(report);
//		            }
//				}
//			}
//
//            workbook.close();
//
//            return reports;
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//    }
    
//    public static String validateExcelFile(Sheet sheet) {
//        String error;
//        
//        Row headerRow = sheet.getRow(0);
//        if (headerRow == null) {
//            error = "Header row is missing";
//            return error;
//        }
//
//        String[] expectedHeaders = {
//            "ID", "Name", "NRC", "DateOfBirth", "Gender", "State", "Phone", "Email", "Address", "Hobby",
//            "Myanmar", "English", "Mathematic", "History", "Science", "Total", "Year"
//        };
//
//        for (int i = 0; i < expectedHeaders.length; i++) {
//            Cell cell = headerRow.getCell(i);
//            if (cell == null || !expectedHeaders[i].equals(cell.getStringCellValue())) {
//                return error = "Header does not match at column " + (i + 1);
//            }
//        }
//
//        Iterator<Row> rows = sheet.iterator();
//        int rowNumber = 0;
//        while (rows.hasNext()) {
//            Row currentRow = rows.next();
//            if(rowNumber == 0) {
//            	continue;
//            }
//
//            for (int cellIdx = 0; cellIdx < expectedHeaders.length; cellIdx++) {
//                Cell currentCell = currentRow.getCell(cellIdx);
//
//                switch (cellIdx) {
//                    case 0: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                       }
//                    	Optional<Student> stuOpt = studentRepository.findByStudentID(currentCell.toString());
//                    	if(stuOpt.isPresent()) {
//                    		return error = "Student ID must be unique.";
//                    	}
//                    	break;
//                    case 1: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                       }
//                    	if(currentCell.getCellType() != CellType.STRING) {
//                    		return error = "Name must be text.";
//                    	}
//                    	break;
//                    case 2: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                       }
//                    	Optional<Student> stuOptN = studentRepository.findByNrc(currentCell.toString());
//                    	if(stuOptN.isPresent()) {
//                    		return error = "NRC must be unique";
//                    	}
//                    	break;
//                    case 4: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                        }
//                    	if(currentCell.toString().toUpperCase() != "MALE" || currentCell.toString().toUpperCase() != "FEMALE") {
//                    		return error="Gender Field must be \'MALE\' or \'FEMALE\'";
//                    	}
//                    case 5: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                        }
//                    	List<State> states = Arrays.asList(State.values());
//                    	boolean isExist = states.stream().anyMatch((state) ->  state.toString() == currentCell.toString().toUpperCase());
//                    	if(!isExist) {
//                    		return error="Enter Valid state.";
//                    	}
//                    case 7: 
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                        }
//                    	Optional<Student> stuOptE = studentRepository.findByEmail(currentCell.toString());
//                    	if(stuOptE.isPresent()) {
//                    		return error ="Email must be unique";
//                    	}
//                    case 8: // Address
//                    	if (currentCell == null) {
//                            return error="Missing data at row " + (rowNumber + 1) + ", column " + (cellIdx + 1);
//                        }
//                    case 9: // Hobby
//                        if (currentCell.getCellType() != CellType.STRING) {
//                            return error ="Enter valid text for hobby.";
//                        }
//                        break;
//                    case 3: // DateOfBirth
//                        if (!DateUtil.isCellDateFormatted(currentCell)) {
//                        	return error ="Enter valid date format.";
//                        }
//                        break;
//                    case 6: // Phone
//                        if (currentCell.getCellType() != CellType.NUMERIC) {
//                            return error ="Phonenumber must be numeric";
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
//            rowNumber++;
//        }
//        return "valid";
//    }
    
    public static boolean validateExcelFile(MultipartFile file) {
    	
    	System.out.println("inside validate excelfile");
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
		Sheet sheet = workbook.getSheetAt(0);
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            return false;
        }

        String[] expectedHeaders = {
            "ID", "Name", "NRC", "DateOfBirth", "Gender", "State", "Phone", "Email", "Address", "Hobby",
            "Myanmar", "English", "Mathematic", "History", "Science", "Total", "Year"
        };

        for (int i = 0; i < expectedHeaders.length; i++) {
            Cell cell = headerRow.getCell(i);
            if (cell == null || !expectedHeaders[i].equals(cell.getStringCellValue())) {
                return false;
            }
        }

        Iterator<Row> rows = sheet.iterator();
        int rowNumber = 0;
        while (rows.hasNext()) {
        	System.out.println("pass rows iterator while loop.");
            Row currentRow = rows.next();
            if(rowNumber == 0) {
            	rowNumber++;
            	continue;
            }

            for (int cellIdx = 0; cellIdx < expectedHeaders.length; cellIdx++) {
                Cell currentCell = currentRow.getCell(cellIdx);
                System.out.println(cellIdx);
                switch (cellIdx) {
                    case 0: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                       }
                        System.out.println(currentCell.toString());
                    	Optional<Student> stuOpt = studentRepository.findByStudentID(currentCell.toString());
                    	if(stuOpt.isPresent()) {
                    		return false;
                    	}
                    	break;
                    case 1: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                       }
                        System.out.println(currentCell.toString());
                    	if(currentCell.getCellType() != CellType.STRING) {
                    		return false;
                    	}
                    	break;
                    case 2: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                       }
                        System.out.println(currentCell.toString());
                    	Optional<Student> stuOptN = studentRepository.findByNrc(currentCell.toString());
                    	if(stuOptN.isPresent()) {
                    		return false;
                    	}
                    	break;
                    case 3: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                       }
                        System.out.println(currentCell.toString().isBlank());
                    	CellStyle cellStyle = currentCell.getCellStyle();
                        short dataFormat = cellStyle.getDataFormat();
                        String formatString = workbook.getCreationHelper().createDataFormat().getFormat(dataFormat);
                        
                        if (!"yyyy\\-mm\\-dd".equals(formatString)) {
                        	 System.out.println("Date format is incorrect. Expected 'yyyy\\-mm\\-dd' but got: " + formatString);
                        	 return false;
                        }
                        break;
                    case 4: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                        }
                    	System.out.println(currentCell.toString());
                    	List<Gender> genders = Arrays.asList(Gender.values());
                    	Optional<Gender> isGenderExist = genders.stream().filter(gender -> gender.toString().equals(currentCell.toString().toUpperCase())).findFirst();
                    	if(isGenderExist.isEmpty()) {
                    		return false;
                    	}
                    	break;
                    case 5: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                        }
                        System.out.println(currentCell.toString());
                    	List<State> states = Arrays.asList(State.values());
                    	boolean isExist = states.stream().anyMatch((state) ->  state.toString().equals(currentCell.toString().toUpperCase()));
                    	if(!isExist) {
                    		return false;
                    	}
                    	break;
                    case 6: 
                        if (currentCell == null || currentCell.toString().isBlank() || currentCell.getCellType() != CellType.NUMERIC) {
                            return false;
                        }
                        System.out.println(currentCell.toString());
                        break;
                    case 7: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                        }
                        System.out.println(currentCell.toString());
                    	Optional<Student> stuOptE = studentRepository.findByEmail(currentCell.toString());
                    	if(stuOptE.isPresent()) {
                    		return false;
                    	}
                    	break;
                    case 8: 
                    	if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                        }
                        System.out.println(currentCell.toString());
                        break;
                    case 9: 
                        if (currentCell == null || currentCell.toString().isBlank()) {
                            return false;
                        }
                        System.out.println(currentCell.toString());
                        break;
                    default:
                        break;
                }
            }
            rowNumber++;
        }
        return true;

		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException("fail to validate excel data: " + e.getMessage());
		}
    }


}

