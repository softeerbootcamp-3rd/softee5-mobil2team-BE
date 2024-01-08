package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/post")
@CrossOrigin(origins = "http://localhost:5173")
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

    /* 좋아요 수 업데이트 */
    @PostMapping("/like")
    public ResponseEntity<ResponseDto> updateLiked(
            @RequestParam(value = "id") Long id,
            @RequestParam(value = "count") int cnt) {

        if (postService.updateLiked(id, cnt)) {
            return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK, "좋아요 수 업데이트 성공"), HttpStatus.OK);
        }
        throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 포스트입니다.");
    }

    /* 게시글 리스트 조회 */
    @GetMapping("/postList")
    public ResponseEntity<PageResponseDto<PostListDto>> getPostList(
            @RequestParam(value = "stationId") Long id,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "tagId", required = false) Long tagId) {
        return new ResponseEntity<>(postService.getPostList(id, pageSize, pageNumber, tagId), HttpStatus.OK);
    }
}
