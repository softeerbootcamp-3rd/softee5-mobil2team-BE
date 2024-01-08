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
@Schema(name = "Page Info Response")
public class PageInfoDto {
    @Schema(description = "요청하는 페이지 번호")
    private Integer page;
    @Schema(description = "한 페이지 당 조회할 데이터 수")
    private Integer size;
    @Schema(description = "전체 데이터 개수 (페이지 관계 X)")
    private Long totalElements;
    @Schema(description = "전체 페이지 수")
    private Integer totalPages;
}
