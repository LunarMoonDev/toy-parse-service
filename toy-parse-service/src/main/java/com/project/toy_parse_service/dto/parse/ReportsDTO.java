package com.project.toy_parse_service.dto.parse;

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
public class ReportsDTO {
    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String id;
}
