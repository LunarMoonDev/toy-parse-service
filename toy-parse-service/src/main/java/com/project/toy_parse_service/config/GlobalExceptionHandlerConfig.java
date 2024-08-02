package com.project.toy_parse_service.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.exceptions.GenericException;
import com.project.toy_parse_service.util.MapperUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler({ GenericException.class })
    public ResponseEntity<ParseResponseDTO> handleLocalException(GenericException exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MapperUtil.toPlayerResponseDTO(exception));
    }

    @ExceptionHandler({ MissingRequestHeaderException.class })
    public ResponseEntity<ParseResponseDTO> handleMissingHeaders(
            MissingRequestHeaderException exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(MapperUtil.toPlayerResponseDTO(Errors.SERVICE_HEADER));
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ParseResponseDTO> handleOtherException(Exception exception) {
        log.error("Exception: {}", exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(MapperUtil.toPlayerResponseDTO(Errors.SERVICE_ERROR));
    }
}
