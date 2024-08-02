package com.project.toy_parse_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.entity.Reports;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.enums.Success;
import com.project.toy_parse_service.exceptions.GenericException;
import com.project.toy_parse_service.repository.ReportsRepository;
import com.project.toy_parse_service.service.impl.FileStorageServiceImpl;
import com.project.toy_parse_service.service.impl.ParseServiceImpl;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.math.BigInteger;

public class ParseServiceTest {
    @InjectMocks
    private ParseServiceImpl service;

    @Mock
    private FileStorageServiceImpl fileService;

    @Mock
    private ReportsRepository repository;

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

    private Reports creaReports() {
        return Reports.builder().build();
    }

    // test
    @Test
    public void testSave_Success() throws IOException {
        MockMultipartFile file = createMockFile();
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just("Some string"));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        ParseResponseDTO response = service.save(uuid, file, playerId).block();

        assertNotNull(response);
        assertEquals(response.getCode(), Success.PARSE_UPLOAD_SUCCESS.getCode());
        assertEquals(response.getMessage(), Success.PARSE_UPLOAD_SUCCESS.getMessage());
    }

    @Test
    public void testSave_FailNullability() throws IOException {
        MockMultipartFile file = null;
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.PARSE_MISSING_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.PARSE_MISSING_ERROR.getMessage());
    }

    @Test
    public void testSave_FailCsv() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "2015_06_04_report.txt", "text/plain",
                "some,some".getBytes());
        ;
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.PARSE_TYPE_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.PARSE_TYPE_ERROR.getMessage());
    }

    @Test
    public void testSave_FailFormat() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "asdf.csv", "text/csv",
                "some,some".getBytes());
        ;
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.PARSE_FILE_NAME_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.PARSE_FILE_NAME_ERROR.getMessage());
    }

    @Test
    public void testSave_FailFormat_Date() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "2056_GG_78_report.csv", "text/csv",
                "some,some".getBytes());
        ;
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.PARSE_FILE_NAME_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.PARSE_FILE_NAME_ERROR.getMessage());
    }

    @Test
    public void testSave_FailFormat_FileName() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "2056_09_01_reOort.csv", "text/csv",
                "some,some".getBytes());
        ;
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenReturn(reports);

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.PARSE_FILE_NAME_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.PARSE_FILE_NAME_ERROR.getMessage());
    }

    @Test
    public void testSave_FailServer() throws IOException {
        MockMultipartFile file = createMockFile();
        String uuid = createUuid();
        BigInteger playerId = createPlayerId();
        Reports reports = creaReports();

        when(fileService.upload(uuid, file)).thenReturn(Mono.just(""));
        when(repository.save(any(Reports.class))).thenThrow(new RuntimeException());

        GenericException exception = assertThrows(GenericException.class, () -> {
            ParseResponseDTO response = service.save(uuid, file, playerId).block();
        });

        assertNotNull(exception);
        assertEquals(exception.getCode(), Errors.SERVICE_ERROR.getCode());
        assertEquals(exception.getMessage(), Errors.SERVICE_ERROR.getMessage());
    }
}
