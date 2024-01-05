package com.softee5.mobil2team.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BriefInfoDto {

    private List<StationInfo> stations;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class StationInfo {

        private Long stationId;
        private String stationName;
        private Long tagId;
        private Integer contentCount;
    }
}
