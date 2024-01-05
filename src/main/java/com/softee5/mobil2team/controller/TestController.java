package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TestDto;
import com.softee5.mobil2team.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ResponseEntity<DataResponseDto<TestDto>> getTest() {
        return new ResponseEntity<>(testService.testMethod(), HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<DataResponseDto<TestDto>> postTest() {
        return new ResponseEntity<>(testService.testMethod(), HttpStatus.OK);
    }
}