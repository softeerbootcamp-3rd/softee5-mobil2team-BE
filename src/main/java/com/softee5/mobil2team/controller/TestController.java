package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TestDto;
import com.softee5.mobil2team.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="00. 테스트용 API", description = "서버 동작 테스트용 API")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    @Operation(summary = "Get 테스트", description = "GET 메서드 테스트")
    public ResponseEntity<DataResponseDto<TestDto>> getTest() {
        return new ResponseEntity<>(testService.testMethod(), HttpStatus.OK);
    }

    @PostMapping("/test")
    @Operation(summary = "Post 테스트", description = "POST 메서드 테스트")
    public ResponseEntity<DataResponseDto<TestDto>> postTest() {
        return new ResponseEntity<>(testService.testMethod(), HttpStatus.OK);
    }
}