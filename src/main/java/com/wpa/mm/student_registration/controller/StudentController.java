package com.wpa.mm.student_registration.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.service.MapValidationService;
import com.wpa.mm.student_registration.service.PDFService;
import com.wpa.mm.student_registration.service.StudentService;
import com.wpa.mm.student_registration.util.ExcelHelper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"})
public class StudentController {

	private final StudentService studentService;
	private final MapValidationService mapValidationService;
	private final PDFService pdfService;
	
	@GetMapping("/fetch")
	public ResponseEntity<List<Student>> getAllStudents(){
		return new ResponseEntity<List<Student>>(studentService.getAllStudents(), HttpStatus.OK);
	}
	
	@GetMapping("/{studentId}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long studentId){
		Optional<Student> studentOpt = studentService.getStudentById(studentId);
		if(studentOpt.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Student>(studentOpt.get(), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createStudent(@Valid @RequestBody Student student,BindingResult result){
		if(result.hasErrors()) {
			return mapValidationService.validate(result);
		}
		return new ResponseEntity<Student>(studentService.createStudent(student), HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateStudent(@Valid @RequestBody Student student,BindingResult result){
		if(result.hasErrors()) {
			return mapValidationService.validate(result);
		}
		return new ResponseEntity<Student>(studentService.updateStudent(student), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{studentId}")
	public ResponseEntity<Long> deleteStudent(@PathVariable Long studentId){
		boolean success = studentService.deleteStudentById(studentId);
		if(!success) {
			return new ResponseEntity<Long>(studentId, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Long>(studentId, HttpStatus.OK);
	}
	
	@PostMapping("/import")
    public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file) {
        if (ExcelHelper.hasExcelFormat(file) && ExcelHelper.validateExcelFile(file)) {
            
            return new ResponseEntity<List<Student>>(studentService.save(file), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportStudents() {
        ByteArrayInputStream in = studentService.load();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(in.readAllBytes());
    }
    
    @GetMapping("/export/{studentId}")
    public ResponseEntity<byte[]> exportStudentById(@PathVariable long studentId) {
        ByteArrayInputStream in = studentService.loadById(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=students.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(in.readAllBytes());
    }
    
    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateExcelFile(){
    	ByteArrayInputStream in = studentService.generateFile();
    	HttpHeaders headers = new HttpHeaders();
    	headers.add("Content-Disposition", "attachment; filename=students.xlsx");
    	return ResponseEntity
    			.ok()
    			.headers(headers)
    			.body(in.readAllBytes());
    }
    
    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> exportPDF(){
    	ByteArrayInputStream bis;
		try {
			bis = pdfService.generatePDF();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=students.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
    }
	
}
