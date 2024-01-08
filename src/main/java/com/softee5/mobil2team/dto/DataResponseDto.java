package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class DataResponseDto<T> extends ResponseDto {

    private final T data;

    private DataResponseDto(T data) {
        super(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), null);
        this.data = data;
    }

    private DataResponseDto(T data, String message) {
        super(true, ResponseCode.OK.getCode(), message, null);
        this.data = data;
    }

    private DataResponseDto(T data, PageInfoDto pageInfoDto) {
        super(true, ResponseCode.OK.getCode(), ResponseCode.OK.getMessage(), pageInfoDto);
        this.data = data;
    }

    private DataResponseDto(T data, String message, PageInfoDto pageInfoDto) {
        super(true, ResponseCode.OK.getCode(), message, pageInfoDto);
        this.data = data;

    }

    public static <T> DataResponseDto<T> of(T data) {
        return new DataResponseDto<>(data);
    }

    public static <T> DataResponseDto<T> of(T data, String message) {
        return new DataResponseDto<>(data, message);
    }

    public static <T> DataResponseDto<T> of(T data, PageInfoDto pageInfoDto) {
        return new DataResponseDto<>(data, pageInfoDto);
    }

    public static <T> DataResponseDto<T> of(T data, String message, PageInfoDto pageInfoDto) {
        return new DataResponseDto<>(data, message, pageInfoDto);
    }

    public static <T> DataResponseDto<T> empty() {
        return new DataResponseDto<>(null);
    }
}