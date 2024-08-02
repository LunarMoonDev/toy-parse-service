package com.project.toy_parse_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Success {
    PARSE_UPLOAD_SUCCESS("PRSE-SUCC-01", "Report payload has been uploaded successfully.");

    private final String code;
    private final String message;
}
