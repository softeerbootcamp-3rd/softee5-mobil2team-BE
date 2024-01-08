package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Post Request for upload")
public class PostDto {
    @Schema(description = "역 id", example = "1")
    private Long stationId;
    @Schema(description = "태그 id", example = "2")
    private Long tagId;
    @Schema(description = "글 내용", example = "졸려")
    private String content;
    @Schema(description = "이미지 id", example = "3")
    private Long imageId;
}
