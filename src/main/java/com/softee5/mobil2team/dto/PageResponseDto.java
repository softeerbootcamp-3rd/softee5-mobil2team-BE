package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;
import lombok.Getter;

@Getter
public class PageResponseDto<T> extends DataResponseDto<T> {
    private final PageInfoDto pageInfoDto;

    private PageResponseDto(T data, PageInfoDto pageInfoDto) {
        super(data);
        this.pageInfoDto = pageInfoDto;
    }

    private PageResponseDto(T data, PageInfoDto pageInfoDto, String message) {
        super(data, message);
        this.pageInfoDto = pageInfoDto;
    }

    public static <T> PageResponseDto<T> of(T data, PageInfoDto pageInfoDto) {
        return new PageResponseDto<>(data, pageInfoDto);
    }

    public static <T> PageResponseDto<T> of(T data, PageInfoDto pageInfoDto, String message) {
        return new PageResponseDto<>(data, pageInfoDto, message);
    }
}
