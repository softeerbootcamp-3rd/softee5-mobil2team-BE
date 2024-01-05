package com.softee5.mobil2team.service;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.BriefInfoDto;
import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.StationDto;
import com.softee5.mobil2team.entity.Station;
import com.softee5.mobil2team.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public DataResponseDto<BriefInfoDto> getBriefInfo() {
        return DataResponseDto.of(new BriefInfoDto(stationRepository.getBriefStationInfo()));
    }

    public DataResponseDto<StationDto> nearestStation(double x, double y) {
        List<Long> nearStation = stationRepository.findNearestStations(x, y, 1);
        if (nearStation == null || nearStation.isEmpty())
            throw new GeneralException(ResponseCode.INTERNAL_ERROR);
        return DataResponseDto.of(new StationDto(nearStation.get(0)));
    }
}
