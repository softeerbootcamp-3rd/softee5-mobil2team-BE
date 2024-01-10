package com.softee5.mobil2team.service;

import com.softee5.mobil2team.dto.DataResponseDto;
import com.softee5.mobil2team.dto.TestDto;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public DataResponseDto<TestDto> testMethod() {
        return DataResponseDto.of(new TestDto("hello World"));
    }
}
