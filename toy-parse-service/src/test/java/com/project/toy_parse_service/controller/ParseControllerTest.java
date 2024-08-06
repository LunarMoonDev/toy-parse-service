package com.project.toy_parse_service.controller;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.project.toy_parse_service.controller.v1.ParseController;
import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.dto.parse.response.ReportsResponseDTO;
import com.project.toy_parse_service.enums.Success;
import com.project.toy_parse_service.service.ParseService;
import com.project.toy_parse_service.util.MapperUtil;

import reactor.core.publisher.Mono;
import java.io.IOException;
import java.math.BigInteger;

public class ParseControllerTest {
    @InjectMocks
    private ParseController controller;

    @Mock
    private ParseService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // mocks
    public MockMultipartFile createMockFile() {
        return new MockMultipartFile("data", "2015_06_04_report.csv", "text/csv", "some,some".getBytes());
    }

    private String createUuid() {
        return "Sample";
    }

    private BigInteger createPlayerId() {
        return BigInteger.ONE;
    }

    private ParseResponseDTO createParseResponseDTO() {
        return MapperUtil.toPlayerResponseDTO(Success.PARSE_UPLOAD_SUCCESS);
    }

    private ReportsResponseDTO createReportsResponseDTO() {
        return ReportsResponseDTO.builder()
            .data(Collections.emptyList())
            .build();
    }

    // tests
    @Test
    public void testUploadCsvFile_Success() throws IOException {
        MockMultipartFile file = createMockFile();
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        ParseResponseDTO resp = createParseResponseDTO();

        when(service.save(uuid, file, playerId)).thenReturn(Mono.just(resp));

        ResponseEntity<ParseResponseDTO> response = controller.uploadCsvFile(uuid, file, playerId).block();

        assertNotNull(response);
        assertEquals(Success.PARSE_UPLOAD_SUCCESS.getCode(), response.getBody().getCode());
        assertEquals(Success.PARSE_UPLOAD_SUCCESS.getMessage(), response.getBody().getMessage());
    }

    @Test
    public void testListCsvFile_Success() throws IOException {
        String uuid = createUuid();
        ReportsResponseDTO report = createReportsResponseDTO();

        when(service.list(anyString())).thenReturn(Mono.just(report));

        ResponseEntity<ReportsResponseDTO> response = controller.listCsvFiles(uuid).block();

        assertNotNull(response);
        assertEquals(0, response.getBody().getData().size());
    }
}
