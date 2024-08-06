package com.project.toy_parse_service.service;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.web.multipart.MultipartFile;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.dto.parse.response.ReportsResponseDTO;
import com.project.toy_parse_service.exceptions.GenericException;

import reactor.core.publisher.Mono;

public interface ParseService {
    String DATE_PATTERN = "YYYY_MM_DD";

    Mono<ParseResponseDTO> save(String uuid, MultipartFile payload, BigInteger id) throws GenericException, IOException;
    Mono<ReportsResponseDTO> list(String uuid) throws GenericException, IOException;
}
