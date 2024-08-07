package com.project.toy_parse_service.dto.parse.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.toy_parse_service.dto.parse.ReportsDTO;

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
public class ReportsResponseDTO {
    @JsonProperty("data")
    private List<ReportsDTO> data;
}
