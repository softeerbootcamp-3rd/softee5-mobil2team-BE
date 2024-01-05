package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TagDto;
import com.softee5.mobil2team.dto.TagListDto;
import com.softee5.mobil2team.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/station")
public class StationController {
    private final TagService tagService;

    @GetMapping("/tag/list")
    public ResponseEntity<DataResponseDto<TagListDto>> getAllTags(@RequestParam Long id) {
        return new ResponseEntity<>(tagService.getTagList(id), HttpStatus.OK);
    }
}
