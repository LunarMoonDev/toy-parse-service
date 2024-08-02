package com.project.toy_parse_service.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.project.toy_parse_service.config.FileStorageConfig;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.exceptions.GenericException;
import com.project.toy_parse_service.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileStorageConfig config;

    @Autowired
    private Drive googleDrive;

    @Override
    public Mono<String> upload(String uuid, MultipartFile file) throws IOException {
        log.info("X-Tracker: {} | Uploading payload to gdrive", uuid);
        log.debug("X-Tracker: {} | request payload: {}", file);

        log.info("X-Tracker: {} | Creating metadata...", uuid);
        File fileMetaData = new File();
        fileMetaData.setName(file.getOriginalFilename());
        fileMetaData.setParents(Collections.singletonList(config.getParentFolder()));

        InputStreamContent stream = new InputStreamContent(file.getContentType(),
                new ByteArrayInputStream(file.getBytes()));

        return Mono.fromCallable(() -> googleDrive.files().create(fileMetaData, stream).execute())
                .doOnNext(response -> log.info("X-Tracker: {} | Success in uploading to gdrive", uuid))
                .map(File::getId)
                .doOnError(error -> {
                    log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                    throw new GenericException(Errors.SERVICE_ERROR);
                });
    }
}
