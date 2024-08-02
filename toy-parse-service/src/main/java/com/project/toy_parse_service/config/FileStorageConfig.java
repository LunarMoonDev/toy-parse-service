package com.project.toy_parse_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class FileStorageConfig {
    @Value("${drive.parse.v1.parent-folder}")
    private String parentFolder;
}
