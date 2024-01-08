package com.softee5.mobil2team.controller;

import com.softee5.mobil2team.config.GeneralException;
import com.softee5.mobil2team.config.ResponseCode;
import com.softee5.mobil2team.dto.*;
import com.softee5.mobil2team.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/post")
@Tag(name = "02. 와글와글 관련 API", description = "와글와글 포스트와 관련된 API들")
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
    @Operation(summary = "좋아요 수 업데이트", description = "해당 게시글의 좋아요 수 반영")
    public ResponseEntity<ResponseDto> updateLiked(
            @RequestParam(value = "id") @Parameter(description = "게시글 ID", required = true, example = "83") Long id,
            @RequestParam(value = "count") @Parameter(description = "증가할 좋아요 수", required = true, example = "4") int cnt) {

        if (postService.updateLiked(id, cnt)) {
            return new ResponseEntity<>(ResponseDto.of(true, ResponseCode.OK, "좋아요 수 업데이트 성공"), HttpStatus.OK);
        }
        throw new GeneralException(ResponseCode.BAD_REQUEST, "존재하지 않는 포스트입니다.");
    }

    /* 게시글 리스트 조회 */
    @GetMapping("/postList")
    @Operation(summary = "게시글 리스트 조회", description = "해당하는 역의 24시간 이내에 작성된 게시글 리스트 조회")
    public ResponseEntity<DataResponseDto<PostListDto>> getPostList(
            @RequestParam(value = "stationId") @Parameter(description = "역 ID", required = true, example = "3") Long id,
            @RequestParam(value = "pageSize") @Parameter(description = "페이지 한 개당 요청할 게시글 수", required = true, example = "5") Integer pageSize,
            @RequestParam(value = "pageNumber") @Parameter(description = "요청하는 페이지 번호 (1부터 시작)", required = true, example = "1") Integer pageNumber,
            @RequestParam(value = "tagId", required = false) @Parameter(description = "조회할 태그 ID", required = false, example = "1") Long tagId) {
        return new ResponseEntity<>(postService.getPostList(id, pageSize, pageNumber, tagId), HttpStatus.OK);
    }
}
