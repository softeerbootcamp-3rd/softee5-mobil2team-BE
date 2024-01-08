package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Hot Station Response")
public class HotStationDto {

    @Schema(description = "핫한 역 리스트 최대 3개, 없을 시 빈 리스트로 반환됨",
    example = "[1, 2, 3]")
    List<Long> stations;
}
