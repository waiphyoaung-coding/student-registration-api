package com.wpa.mm.student_registration.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/image")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:4200"})
public class ImageController {
	
	private final Environment env;
	
	@PostMapping("/upload")
	public ResponseEntity<Map<String, String>> imageUpload(@PathParam("file") MultipartFile file) {
		
		String uploadPath = env.getProperty("image_upload_path");
		String originalFileName = file.getOriginalFilename();
		
		String fileNameWithoutExtension = originalFileName != null ? originalFileName.replaceAll("\\.[^.]*$", "") : "default";
		String fileName = fileNameWithoutExtension+".jpg";
		
		Path filePath = Path.of(uploadPath+fileName);
		
		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(Map.of("imageName", fileNameWithoutExtension));
	}
	
	@GetMapping("/{imageName}")
	public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
		ClassPathResource resource = new ClassPathResource("static/images/"+imageName);
		byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
	}

}
