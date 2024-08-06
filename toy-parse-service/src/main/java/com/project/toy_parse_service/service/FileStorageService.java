package com.project.toy_parse_service.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.toy_parse_service.dto.parse.ReportsDTO;

import reactor.core.publisher.Mono;

public interface FileStorageService {
    Mono<String> upload(String uuid, MultipartFile file) throws IOException;
    Mono<List<ReportsDTO>> list(String uuid);
}
