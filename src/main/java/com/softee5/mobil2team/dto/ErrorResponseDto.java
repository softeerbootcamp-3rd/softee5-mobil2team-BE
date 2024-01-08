package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;

public class ErrorResponseDto extends ResponseDto {

    private ErrorResponseDto(ResponseCode errorResponseCode) {
        super(false, errorResponseCode.getCode(), errorResponseCode.getMessage(), null);
    }

    private ErrorResponseDto(ResponseCode errorResponseCode, Exception e) {
        super(false, errorResponseCode.getCode(), errorResponseCode.getMessage(e), null);
    }

    private ErrorResponseDto(ResponseCode errorResponseCode, String message) {
        super(false, errorResponseCode.getCode(), errorResponseCode.getMessage(message), null);
    }


    public static ErrorResponseDto of(ResponseCode errorResponseCode) {
        return new ErrorResponseDto(errorResponseCode);
    }

    public static ErrorResponseDto of(ResponseCode errorResponseCode, Exception e) {
        return new ErrorResponseDto(errorResponseCode, e);
    }

    public static ErrorResponseDto of(ResponseCode errorResponseCode, String message) {
        return new ErrorResponseDto(errorResponseCode, message);
    }
}