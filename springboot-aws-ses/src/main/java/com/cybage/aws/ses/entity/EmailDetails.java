package com.cybage.aws.ses.entity;

import java.util.List;

import lombok.Data;

@Data
public class EmailDetails {
	
	private String fromEmail;
	private List<String> toEmail;
	private String subject;
	private String body;
	
		
}
