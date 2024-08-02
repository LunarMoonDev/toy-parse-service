package com.project.toy_parse_service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Errors {
    PARSE_TYPE_ERROR("PRSE-ERR-01", "Given file type must be in text/csv."),
    PARSE_FILE_NAME_ERROR("PRSE-ERR-02", "Given file name should follow the format YYY_MM_DD_report.csv"),
    PARSE_MISSING_ERROR("PRSE-ERR-03", "Given payload must not be null or blank."),
    SERVICE_HEADER("PRSE-ERR-04", "Please include the required headers."),
    SERVICE_ERROR("PRSE-ERR-05", "Error occured inside the server.");

    private final String code;
    private final String message;
}
