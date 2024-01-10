package com.softee5.mobil2team.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Near Station Response")
public class NearStationDto {

    @Schema(description = "가장 가까운 역 id", example = "24")
    private Long stationId;
}
