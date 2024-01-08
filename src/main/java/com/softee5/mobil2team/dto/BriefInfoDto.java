package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Brief Info Response")
public class BriefInfoDto {

    @Schema(description = "전체 지하철 역 정보")
    private List<StationInfo> stations;

    @Schema(name="Station Info")
    public interface StationInfo {

        @Schema(description = "역 id", example = "1")
        Long getStationId();

        @Schema(description = "역 이름", example = "충정로역")
        String getStationName();

        @Schema(description = "최근 2시간 가장 많이 사용된 태그 id", example = "1")
        Long getTagId();

        @Schema(description = "최근 2시간 생성된 컨텐츠 개수", example = "15")
        Integer getContentCount();
    }
}
