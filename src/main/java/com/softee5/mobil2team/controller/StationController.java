package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /* 가까운 역 리스트 */
    @GetMapping("/near/list")
    public ResponseEntity<DataResponseDto<StationListDto>> getNearStationList(
            @RequestParam(value = "x", required = false) Double x,
            @RequestParam(value = "y", required = false) Double y) {
        return new ResponseEntity<>(
                stationService.getNearStationList(x, y),
                HttpStatus.OK
        );
    }
}
