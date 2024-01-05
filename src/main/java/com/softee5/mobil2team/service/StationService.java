package com.softee5.mobil2team.service;

import com.softee5.mobil2team.dto.BriefInfoDto;
import com.softee5.mobil2team.entity.Station;
import com.softee5.mobil2team.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public BriefInfoDto getBriefInfo() {

        List<Station> stations = stationRepository.findAll();
        BriefInfoDto result = new BriefInfoDto();

        return result;
    }
}
