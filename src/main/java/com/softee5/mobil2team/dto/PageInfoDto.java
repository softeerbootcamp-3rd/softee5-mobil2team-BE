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
public class PageInfoDto {

    @Schema(description = "현재 페이지, 1부터 시작", example = "1")
    private Integer page;

    @Schema(description = "페이지 사이즈", example = "20")
    private Integer size;

    @Schema(description = "총 정보 개수", example = "95")
    private Long totalElements;

    @Schema(description = "총 페이지 개수", example = "5")
    private Integer totalPages;
}
