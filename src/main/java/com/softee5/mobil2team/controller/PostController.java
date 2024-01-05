package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.PostDto;
import com.softee5.mobil2team.dto.TestDto;
import com.softee5.mobil2team.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    /* 글 업로드 */
    @PostMapping("/upload")
    public ResponseEntity<DataResponseDto<Void>> postTest(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                postService.uploadPost(postDto),
                HttpStatus.OK
        );
    }

}
