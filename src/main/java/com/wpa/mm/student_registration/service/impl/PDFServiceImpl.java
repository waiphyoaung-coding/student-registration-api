package com.wpa.mm.student_registration.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.stereotype.Service;

import com.wpa.mm.student_registration.domain.Report;
import com.wpa.mm.student_registration.domain.Student;
import com.wpa.mm.student_registration.repository.StudentRepository;
import com.wpa.mm.student_registration.service.PDFService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PDFServiceImpl implements PDFService{
	
	private final StudentRepository studentRepository;

	@Override
	public ByteArrayInputStream generatePDF() throws IOException {
		// TODO Auto-generated method stub
		
		List<Student> students = (List<Student>) studentRepository.findAll();
		
		PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        
        contentStream.beginText();
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
        contentStream.newLineAtOffset(10, 750);
        contentStream.showText("Student Registration Data");
        contentStream.endText();
        
        float margin = 50;
        float yStart = 700;
        float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
        float yPosition = yStart;
        
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 8);
        
        String[] headers = {
        		"ID", "Name", "NRC", "DateOfBirth", "Gender", "State", "Phone", "Email", "Address", "Hobby",
                "MMR", "Eng", "Math", "Hty", "Sci", "Total", "Year"
        };
        float[] colWidths = {30, 50, 60, 50, 30, 40, 50, 60, 60, 90, 10, 10, 10, 10, 10, 10, 10};
        
        yPosition = drawTableHeaders(contentStream, yPosition, headers, colWidths, tableWidth);
        
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 7);
        
        for (Student student : students) {
        	
        	String id = student.getStudentID();
            String name = student.getName();
            String nrc = student.getNrc();
            String dob = student.getDob().toString();
            String gender = student.getGender().toString();
            String state = student.getState().toString();
            String phoneNumber = student.getPhonenumber().toString();
            String email = student.getEmail();
            String address = student.getAddress();
            String hobby = String.join(", ", student.getHobby());
            
            if(!student.getReports().isEmpty()) {

                for(Report report : student.getReports()) {

                	String myanmar = report.getMyanmar().toString();
                	String english = report.getEnglish().toString();
                	String mathematic = report.getMathematic().toString();
                	String history = report.getHistory().toString();
                	String science = report.getScience().toString();
                	String total = report.getTotal().toString();
                	String year = report.getAcademicYear().toString();
                    yPosition = drawTableRow(
                    		contentStream, 
                    		yPosition, 
                    		new String[]{id, name, nrc, dob, gender, state, phoneNumber, email, address, hobby, myanmar, english, mathematic, history, science, total, year}, 
                    		colWidths, 
                    		tableWidth);
                }
                
            }else {
            	yPosition = drawTableRow(
            			contentStream, 
            			yPosition, 
            			new String[]{id, name, nrc, dob, gender, state, phoneNumber, email, address, hobby}, 
            			colWidths, 
            			tableWidth);
            }

            if (yPosition < margin) {
                contentStream.close();
                page = new PDPage();
                document.addPage(page);
                contentStream = new PDPageContentStream(document, page);
                yPosition = yStart;
            }
        }
        contentStream.close();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.save(out);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
	}
	
	
	private float drawTableHeaders(PDPageContentStream contentStream, float y, String[] headers, float[] colWidths, float tableWidth) throws IOException {
        float x = 10;
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 8);
        for (int i = 0; i < headers.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(headers[i]);
            contentStream.endText();
            x += colWidths[i];
        }
        y -= 20;
        return y;
    }
	
	
	private float drawTableRow(PDPageContentStream contentStream, float y, String[] row, float[] colWidths, float tableWidth) throws IOException {
        float x = 10;
        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 7);
        for (int i = 0; i < row.length; i++) {
            contentStream.beginText();
            contentStream.newLineAtOffset(x, y);
            contentStream.showText(row[i]);
            contentStream.endText();
            x += colWidths[i];
        }
        y -= 20;
        return y;
    }
	

}
