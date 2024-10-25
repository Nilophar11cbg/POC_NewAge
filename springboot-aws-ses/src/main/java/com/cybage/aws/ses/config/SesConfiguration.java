package com.cybage.aws.ses.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;


@Configuration
public class SesConfiguration {
	
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secret-key}")
	private String secreteKey;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Bean
	public AmazonSimpleEmailService amazonSimpleEmailService() {
		BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secreteKey);
		return AmazonSimpleEmailServiceClientBuilder.standard()
				.withRegion(region).withCredentials(
						new AWSStaticCredentialsProvider(basicAWSCredentials)).build();
	}
	
	

}
