package com.mukund.filestore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix = "files")
@Data
@Configuration
public class FileStoreConfig {

	private String basePath;

}
