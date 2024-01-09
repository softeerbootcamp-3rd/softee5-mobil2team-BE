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
@Schema(name = "Like Request for update")
public class LikeDto {
    @Schema(description = "포스트 ID", example = "3")
    private Long id;
    @Schema(description = "증가할 좋아요 수", example = "4")
    private Integer count;
}
