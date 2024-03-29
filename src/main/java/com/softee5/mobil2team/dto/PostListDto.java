package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Post List Response")
public class PostListDto {
    @Schema(description = "역 별 게시글 리스트 정보")
    private List<PostInfoDto> posts;
}
