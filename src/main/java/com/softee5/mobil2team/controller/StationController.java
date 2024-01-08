package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.service.StationService;
import com.softee5.mobil2team.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/station")
@Tag(name = "01. 역 관련 API", description = "지하철 역과 관련된 API들")
public class StationController {

    @Autowired
    private StationService stationService;
    private final TagService tagService;

    @GetMapping("/briefInfo")
    @Operation(summary = "메인 페이지 로딩용 정보들", description = "전체 역 리스트 및 컨텐츠 현황 조회")
    public ResponseEntity<DataResponseDto<BriefInfoDto>> getBriefInfo() {
        return new ResponseEntity<>(stationService.getBriefInfo(), HttpStatus.OK);
    }

    @GetMapping("/near")
    @Operation(summary = "가장 가까운 역", description = "현재 나의 좌표와 가장 가까운 역의 id")
    public ResponseEntity<DataResponseDto<NearStationDto>> getNearStation(
            @RequestParam @Parameter(description = "현재 x좌표", required = true, example = "37.492817") Double x,
            @RequestParam @Parameter(description = "현재 y좌표", required = true, example = "127.013838") Double y
    ) {
        if (x == null || y == null)
            throw new GeneralException(ResponseCode.BAD_REQUEST);
        return new ResponseEntity<>(stationService.getNearestStation(x, y), HttpStatus.OK);
    }

    @GetMapping("/hot")
    @Operation(summary = "핫한 역 정보", description = "최근 2시간 가장 글이 많이 올라온 역을 최대 3개까지 조회")
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
    @Operation(summary = "태그 리스트 조회", description = "각 역 별로 핫한 태그 리스트 순서 조회")
    public ResponseEntity<DataResponseDto<TagListDto>> getAllTags(
            @RequestParam(value = "id") @Parameter(description = "요청하는 역 ID", required = true, example = "3") Long id) {
        return new ResponseEntity<>(tagService.getTagList(id), HttpStatus.OK);
    }
}
