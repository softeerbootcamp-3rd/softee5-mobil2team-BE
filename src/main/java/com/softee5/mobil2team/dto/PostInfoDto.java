package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Post Info Response")
public class PostInfoDto {
    @Schema(description = "게시글 ID")
    private Long id;
    @Schema(description = "작성자 닉네임")
    private String nickname;
    @Schema(description = "게시글 작성 시간")
    private Date createdTime;
    @Schema(description = "게시글 내용")
    private String Content;
    @Schema(description = "첨부한 이미지 URL")
    private String imageUrl;
    @Schema(description = "태그 ID")
    private Long tagId;
    @Schema(description = "좋아요 수")
    private Integer like;
}
