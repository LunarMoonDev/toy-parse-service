package com.project.toy_parse_service.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.dto.parse.response.ReportsResponseDTO;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.enums.Success;
import com.project.toy_parse_service.exceptions.GenericException;
import com.project.toy_parse_service.repository.ReportsRepository;
import com.project.toy_parse_service.service.FileStorageService;
import com.project.toy_parse_service.service.ParseService;
import com.project.toy_parse_service.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service("ParseService_STABLE")
@Slf4j
public class ParseServiceImpl implements ParseService {
    @Autowired
    private FileStorageService service;

    @Autowired
    private ReportsRepository repository;

    @Override
    public Mono<ParseResponseDTO> save(String uuid, MultipartFile payload, BigInteger id)
            throws GenericException, IOException {
        log.info("X-Tracker: {} | Uploading report for given request...", uuid);
        log.debug("X-Tracker: {} | request payload: {}", payload);

        // check nullability
        log.info("X-Tracker: {} | Checking for nullability of payload...", uuid);
        if (Objects.isNull(payload) || payload.isEmpty()) {
            throw new GenericException(Errors.PARSE_MISSING_ERROR);
        }

        // check csv
        log.info("X-Tracker: {} | Checking for content type...", uuid);
        if (!payload.getContentType().equalsIgnoreCase("text/csv")) {
            throw new GenericException(Errors.PARSE_TYPE_ERROR);
        }

        boolean isInvalid = false;
        // check file name
        log.info("X-Tracker: {} | Checking for file name format...", uuid);
        if (payload.getOriginalFilename().length() != 21) {
            isInvalid = true;
        } else {
            String dateString = payload.getOriginalFilename().substring(0, 10);
            String restString = payload.getOriginalFilename().substring(10);

            try {
                DateTimeFormatter.ofPattern(DATE_PATTERN).parse(dateString);
            } catch (Exception e) {
                isInvalid = true;
            }

            if (!restString.equals("_report.csv")) {
                isInvalid = true;
            }
        }

        if (isInvalid) {
            throw new GenericException(Errors.PARSE_FILE_NAME_ERROR);
        }

        return service.upload(uuid, payload)
                .map(reportId -> MapperUtil.toReports(id, reportId))
                .doOnNext(report -> repository.save(report))
                .doOnNext(report -> log.info("X-Tracker: {} | Success in saving report in database.", uuid))
                .map(report -> MapperUtil.toPlayerResponseDTO(Success.PARSE_UPLOAD_SUCCESS))
                .doOnError(error -> {
                    log.error("X-Tracker: {} | specific exception: {}", uuid, error.getMessage());

                    throw new GenericException(Errors.SERVICE_ERROR);
                });
    }

    @Override
    public Mono<ReportsResponseDTO> list(String uuid) throws GenericException, IOException {
        log.info("X-Tracker: {} | Retrieving list of reports uploaded", uuid);

        try {
            return service.list(uuid)
                    .map(MapperUtil::toReportsResponseDTO);
        } catch (RuntimeException exception) {
            log.error("X-Tracker: {} | specific exception: {}", uuid, exception.getMessage());

            throw new GenericException(Errors.SERVICE_ERROR);
        }
    }
}
