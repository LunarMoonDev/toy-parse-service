package com.project.toy_parse_service.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

public interface FileStorageService {
    Mono<String> upload(String uuid, MultipartFile file) throws IOException;
}
