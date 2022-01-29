package com.phonebookapp.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "phonebookapp")
@EnableConfigurationProperties
public class YAMLConfig {

	private Map<String, String> messages = new HashMap<>();
	
	
}
