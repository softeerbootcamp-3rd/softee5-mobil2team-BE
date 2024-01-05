package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.BriefInfoDto;
import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.StationDto;
import com.softee5.mobil2team.dto.TestDto;
import com.softee5.mobil2team.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/briefInfo")
    public ResponseEntity<DataResponseDto<BriefInfoDto>> getTest() {
        return new ResponseEntity<>(stationService.getBriefInfo(), HttpStatus.OK);
    }

    @GetMapping("/nearStation")
    public ResponseEntity<DataResponseDto<StationDto>> nearStation() {
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
