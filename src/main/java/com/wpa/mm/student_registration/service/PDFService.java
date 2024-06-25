package com.wpa.mm.student_registration.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public interface PDFService {
	
	ByteArrayInputStream generatePDF() throws IOException;

}
