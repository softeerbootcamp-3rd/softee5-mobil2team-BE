package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {

    private final Boolean success;
    private final Integer code;
    private final String message;
    private final PageInfoDto pageInfoDto;

    public static ResponseDto of(Boolean success, ResponseCode code) {
        return new ResponseDto(success, code.getCode(), code.getMessage(), null);
    }

    public static ResponseDto of(Boolean success, ResponseCode code, PageInfoDto pageInfoDto) {
        return new ResponseDto(success, code.getCode(), code.getMessage(), pageInfoDto);
    }

    public static ResponseDto of(Boolean success, ResponseCode errorCode, Exception e) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(e), null);
    }

    public static ResponseDto of(Boolean success, ResponseCode errorCode, String message) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(message), null);
    }
}