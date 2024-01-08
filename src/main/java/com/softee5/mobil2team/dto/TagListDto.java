package com.softee5.mobil2team.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Tag List Response")
public class TagListDto {

    @Schema(description = "역 별 태그 리스트 순서 정보")
    private List<Long> tags;
}
