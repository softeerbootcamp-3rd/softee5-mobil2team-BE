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
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/station")
public class StationController {

    @Autowired
    private StationService stationService;
    private final TagService tagService;

    @GetMapping("/briefInfo")
    public ResponseEntity<DataResponseDto<BriefInfoDto>> getBriefInfo() {
        return new ResponseEntity<>(stationService.getBriefInfo(), HttpStatus.OK);
    }

    @GetMapping("/near")
    public ResponseEntity<DataResponseDto<StationDto>> getNearStation(
            @RequestParam Double x,
            @RequestParam Double y
    ) {
        if (x == null || y == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST);
        return new ResponseEntity<>(stationService.getNearestStation(x, y), HttpStatus.OK);
    }

    @GetMapping("/hot")
    public ResponseEntity<DataResponseDto<HotStationDto>> getHotStation() {
        return new ResponseEntity<>(stationService.getHotStation(), HttpStatus.OK);
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
    public ResponseEntity<DataResponseDto<TagListDto>> getAllTags(@RequestParam(value = "id") Long id) {
        if (id < 1 || id > 44) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 역 ID입니다.");
        }
        return new ResponseEntity<>(tagService.getTagList(id), HttpStatus.OK);
    }
}
