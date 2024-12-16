package com.example.pdftoimageocr.service;

import opennlp.tools.namefind.*;
import opennlp.tools.util.Span;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class OpenNLPService {

	/*
	 * @Value("${opennlp.model.path}") private String modelPath;
	 * 
	 * private TokenNameFinderModel model;
	 * 
	 * public OpenNLPService() throws Exception { try { File modelFile = new
	 * File(modelPath); this.model = new TokenNameFinderModel(modelFile); } catch
	 * (Exception e) { System.err.println("Failed to load OpenNLP model: " +
	 * e.getMessage()); throw e; } }
	 */
    
    private TokenNameFinderModel model;

    public OpenNLPService() throws IOException {
        // Load the model from an absolute path
        File modelFile = new File("C:/openNLP/data/en-field-model.bin");
        this.model = new TokenNameFinderModel(new FileInputStream(modelFile));
    }

    public Map<String, String> extractEntities(String text) {
        System.out.println("Input Text for Entity Extraction: \n" + text); // Log input text

        NameFinderME nameFinder = new NameFinderME(model);
        String[] tokens = text.split("\\s+");
        System.out.println("Tokens: " + Arrays.toString(tokens));
        Span[] spans = nameFinder.find(tokens);

        Map<String, String> extractedData = new HashMap<>();
        for (Span span : spans) {
            StringBuilder entityValue = new StringBuilder();
            for (int i = span.getStart(); i < span.getEnd(); i++) {
                entityValue.append(tokens[i]).append(" ");
            }
            extractedData.put(span.getType(), entityValue.toString().trim());
        }
        
       // nameFinder.clearAdaptiveData();

        System.out.println("Extracted Entities: " + extractedData); // Log extracted entities
        return extractedData;
    }

}
