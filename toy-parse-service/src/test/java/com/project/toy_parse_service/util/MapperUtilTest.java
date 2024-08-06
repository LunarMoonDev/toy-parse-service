package com.project.toy_parse_service.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.api.services.drive.model.File;
import com.project.toy_parse_service.dto.parse.ReportsDTO;
import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.entity.Reports;
import com.project.toy_parse_service.enums.Errors;
import com.project.toy_parse_service.enums.Success;
import com.project.toy_parse_service.exceptions.GenericException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

public class MapperUtilTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToReports() {
        BigInteger playerId = BigInteger.ONE;
        String reportId = "123";

        Reports reports = MapperUtil.toReports(playerId, reportId);

        assertNotNull(reports);
        assertEquals(reportId, reports.getReportId());
        assertEquals(playerId, reports.getPlayerId());
    }

    @Test
    public void testToPlayerResponseDTO_GenericException() {
        GenericException exception = new GenericException(Errors.SERVICE_ERROR);

        ParseResponseDTO responseDTO = MapperUtil.toPlayerResponseDTO(exception);

        assertNotNull(responseDTO);
        assertEquals(responseDTO.getCode(), responseDTO.getCode());
        assertEquals(responseDTO.getMessage(), responseDTO.getMessage());
    }

    @Test
    public void testToPlayerResponseDTO_Success() {
        ParseResponseDTO responseDTO = MapperUtil.toPlayerResponseDTO(Success.PARSE_UPLOAD_SUCCESS);

        assertNotNull(responseDTO);
        assertEquals(Success.PARSE_UPLOAD_SUCCESS.getCode(), responseDTO.getCode());
        assertEquals(Success.PARSE_UPLOAD_SUCCESS.getMessage(), responseDTO.getMessage());
    }

    @Test
    public void testToPlayerResponseDTO_Error() {
        ParseResponseDTO responseDTO = MapperUtil.toPlayerResponseDTO(Errors.SERVICE_ERROR);

        assertNotNull(responseDTO);
        assertEquals(Errors.SERVICE_ERROR.getCode(), responseDTO.getCode());
        assertEquals(Errors.SERVICE_ERROR.getMessage(), responseDTO.getMessage());
    }

    @Test
    public void testToReportsDTO() {
        File file = Mockito.mock(File.class);

        when(file.getName()).thenReturn("name");
        when(file.getId()).thenReturn("id");

        ReportsDTO reportsDTO = MapperUtil.toReportsDTO(file);

        assertNotNull(reportsDTO);
        assertNotNull(file.getId(), reportsDTO.getId());
        assertNotNull(file.getName(), reportsDTO.getName());
    }
}
