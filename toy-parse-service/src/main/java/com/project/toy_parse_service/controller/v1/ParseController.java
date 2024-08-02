package com.project.toy_parse_service.controller.v1;

import java.io.IOException;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.project.toy_parse_service.dto.parse.response.ParseResponseDTO;
import com.project.toy_parse_service.exceptions.GenericException;
import com.project.toy_parse_service.service.ParseService;

import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController("ParseControllerV1")
@RequestMapping("/v1.0/parse")
public class ParseController {
    @Autowired
    @Qualifier("ParseService_STABLE")
    private ParseService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ParseResponseDTO>> uploadCsvFile(@RequestHeader("X-Tracker") String uuid,
            @RequestParam("report") MultipartFile report,
            @RequestParam("player_id") BigInteger id)
            throws GenericException, IOException {
        return service.save(uuid, report, id).map(ResponseEntity::ok);
    }
}
