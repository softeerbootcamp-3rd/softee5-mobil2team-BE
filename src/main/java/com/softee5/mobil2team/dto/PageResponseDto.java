package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageResponseDto<T> extends ResponseDto {

    @Schema(description = "페이징 정보")
    private final PageInfoDto pageInfo;

    private final T data;

    private PageResponseDto(T data, PageInfoDto pageInfo) {
        super(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage());
        this.pageInfo = pageInfo;
        this.data = data;
    }

    private PageResponseDto(T data, PageInfoDto pageInfo, String message) {
        super(true, ResponseCode.OK.getCode(), message);
        this.pageInfo = pageInfo;
        this.data = data;

    }

    public static <T> PageResponseDto<T> of(T data, PageInfoDto pageInfoDto) {
        return new PageResponseDto<>(data, pageInfoDto);
    }

    public static <T> PageResponseDto<T> of(T data, PageInfoDto pageInfoDto, String message) {
        return new PageResponseDto<>(data, pageInfoDto, message);
    }

    public static <T> PageResponseDto<T> empty() {
        return new PageResponseDto<>(null, null);
    }
}
