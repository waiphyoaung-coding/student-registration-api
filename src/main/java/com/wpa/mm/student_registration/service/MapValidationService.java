package com.wpa.mm.student_registration.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface MapValidationService {

	ResponseEntity<Map<String, String>> validate(BindingResult result);
}
