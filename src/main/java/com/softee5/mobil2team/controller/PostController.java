package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.ImageListDto;
import com.softee5.mobil2team.dto.PostDto;
import com.softee5.mobil2team.dto.TestDto;
import com.softee5.mobil2team.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    /* 글 업로드 */
    @PostMapping("/upload")
    public ResponseEntity<DataResponseDto<Void>> uploadPost(@RequestBody PostDto postDto) {
        return new ResponseEntity<>(
                postService.uploadPost(postDto),
                HttpStatus.OK
        );
    }

    /* 모든 이미지 조회 */
    @GetMapping("/images")
    public ResponseEntity<DataResponseDto<ImageListDto>> getAllImages() {
        return new ResponseEntity<>(
                postService.getAllImages(),
                HttpStatus.OK
        );
    }

}
