package com.project.toy_parse_service.util;

import java.math.BigInteger;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.entity.Reports;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.enums.Success;
import com.project.toy_parse_service.exceptions.GenericException;

public class MapperUtil {

    public static Reports toReports(BigInteger playerId, String reportId) {
        return Reports.builder()
                .playerId(playerId)
                .reportId(reportId).build();
    }

    public static ParseResponseDTO toPlayerResponseDTO(GenericException exception) {
        return ParseResponseDTO.builder()
                .code(exception.getCode())
                .message(exception.getMessage())
                .build();
    }

    public static ParseResponseDTO toPlayerResponseDTO(Success success) {
        return ParseResponseDTO.builder()
                .code(success.getCode())
                .message(success.getMessage())
                .build();
    }

    public static ParseResponseDTO toPlayerResponseDTO(Errors error) {
        return ParseResponseDTO.builder()
                .code(error.getCode())
                .message(error.getMessage())
                .build();
    }
}
