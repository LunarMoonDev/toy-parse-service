package com.project.toy_parse_service.dto.parse.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ParseResponseDTO {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;
}
