package com.softee5.mobil2team.dto;

import com.softee5.mobil2team.config.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class ResponseDto {

    @Schema(description = "성공 여부", example = "true")
    private final Boolean success;

    @Schema(description = "결과 코드", example = "200")
    private final Integer code;

    @Schema(description = "결과 메시지", example = "OK")
    private final String message;

    public static ResponseDto of(Boolean success, ResponseCode code) {
        return new ResponseDto(success, code.getCode(), code.getMessage());
    }

    public static ResponseDto of(Boolean success, ResponseCode errorCode, Exception e) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(e));
    }

    public static ResponseDto of(Boolean success, ResponseCode errorCode, String message) {
        return new ResponseDto(success, errorCode.getCode(), errorCode.getMessage(message));
    }
}