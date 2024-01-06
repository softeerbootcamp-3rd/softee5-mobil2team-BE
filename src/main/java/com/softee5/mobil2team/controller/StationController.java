package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.service.StationService;
import com.softee5.mobil2team.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

@RestController
@RequiredArgsConstructor
@RequestMapping("/station")
public class StationController {

    @Autowired
    private StationService stationService;
    private final TagService tagService;

    @GetMapping("/briefInfo")
    public ResponseEntity<DataResponseDto<BriefInfoDto>> getTest() {
        return new ResponseEntity<>(stationService.getBriefInfo(), HttpStatus.OK);
    }

    @GetMapping("/near")
    public ResponseEntity<DataResponseDto<StationDto>> nearStation(
            @RequestParam Double x,
            @RequestParam Double y
    ) {
        if (x == null || y == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST);
        return new ResponseEntity<>(stationService.nearestStation(x, y), HttpStatus.OK);
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

    /* 역 별 태그 리스트 */
    @GetMapping("/tag/list")
    public ResponseEntity<DataResponseDto<TagListDto>> getAllTags(@RequestParam Long id) {
        return new ResponseEntity<>(tagService.getTagList(id), HttpStatus.OK);
    }
}
