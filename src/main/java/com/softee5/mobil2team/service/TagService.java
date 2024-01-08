package com.softee5.mobil2team.service;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TagDto;
import com.softee5.mobil2team.dto.TagListDto;
import com.softee5.mobil2team.entity.Post;
import com.softee5.mobil2team.entity.Tag;
import com.softee5.mobil2team.repository.PostRepository;
import com.softee5.mobil2team.repository.StationRepository;
import com.softee5.mobil2team.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private StationRepository stationRepository;

    /* 역 별 태그 리스트 */
    public DataResponseDto<TagListDto> getTagList(Long id) {
        // stationID 예외처리
        if (stationRepository.findById(id).isEmpty()) {
            throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 역 ID입니다.");
        }

        return DataResponseDto.of(new TagListDto(tagRepository.getTagList(id)));
    }

}
