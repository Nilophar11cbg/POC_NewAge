package com.example.pdftoimageocr.controller;


/*
 * import org.apache.commons.io.FilenameUtils; import
 * org.springframework.web.bind.annotation.*; import
 * org.springframework.web.multipart.MultipartFile; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.util.StringUtils;
 * 
 * import java.io.File; import java.io.IOException; import java.nio.file.Files;
 * import java.nio.file.Path; import java.nio.file.StandardCopyOption; import
 * java.util.UUID;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/pdf") public class FileUploadController {
 * 
 * // Specify the directory where uploaded files will be saved private static
 * final String UPLOAD_DIR = "C:\\demo\\28 Nov\\uploads\\";
 * 
 * @PostMapping("/extract") public ResponseEntity<String>
 * uploadFile(@RequestParam("file") MultipartFile file) { if (file.isEmpty()) {
 * return ResponseEntity.badRequest().body("No file uploaded"); }
 * 
 * // Ensure the upload directory exists File uploadDir = new File(UPLOAD_DIR);
 * if (!uploadDir.exists()) { uploadDir.mkdirs(); // Create directory if it
 * doesn't exist }
 * 
 * // Generate a unique file name to avoid collision String originalFilename =
 * file.getOriginalFilename(); String extension =
 * FilenameUtils.getExtension(originalFilename); String newFileName =
 * UUID.randomUUID().toString() + "." + extension;
 * 
 * // Create the file path File uploadedFile = new File(UPLOAD_DIR +
 * newFileName);
 * 
 * try { // Save the file to the specified directory
 * file.transferTo(uploadedFile); return
 * ResponseEntity.ok("File uploaded successfully to: " +
 * uploadedFile.getAbsolutePath()); } catch (IOException e) { return
 * ResponseEntity.status(500).body("Failed to upload the file: " +
 * e.getMessage()); } } }
 */




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.pdftoimageocr.service.PDFExtractionService;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFExtractionService pdfExtractionService;
    
    final String UPLOAD_DIR = "C:\\demo\\28 Nov\\uploads\\";

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractFromPDF(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No file uploaded. Please upload a valid PDF."));
        }

        try {
            // Ensure the directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // Create directories if they don't exist
            }

            // Generate a unique file name and save the uploaded file
            File tempFile = new File(UPLOAD_DIR + System.currentTimeMillis() + "_" + file.getOriginalFilename());
            file.transferTo(tempFile);

            // Extract data from the PDF
            Map<String, String> extractedData = pdfExtractionService.extractDataFromPDF(tempFile);

            // Delete the temporary file
            tempFile.delete();

            // Return the extracted data as a JSON response
            return ResponseEntity.ok(extractedData);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while processing the PDF. Please try again."));
        }
    }

}

