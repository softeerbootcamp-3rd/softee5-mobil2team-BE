package com.softee5.mobil2team.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BriefInfoDto {

    private List<StationInfo> stations;

    public interface StationInfo {

        Long getStationId();
        String getStationName();
        Long getTagId();
        Integer getContentCount();
    }
}
