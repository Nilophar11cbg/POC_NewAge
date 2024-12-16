package com.example.pdftoimageocr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pdftoimageocr.util.PDFTextExtractor;

import java.io.File;
import java.util.Map;

@Service
public class PDFExtractionService {

    @Autowired
    private OpenNLPService openNLPService;

    public Map<String, String> extractDataFromPDF(File pdfFile) throws Exception {
        // Extract text from PDF
        String text = PDFTextExtractor.extractText(pdfFile);

        // Use OpenNLP to extract entities
        return openNLPService.extractEntities(text);
    }
}
