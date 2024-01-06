package com.softee5.mobil2team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    @Override
    public boolean equals(Object obj) {
        TagDto dto = (TagDto) obj;

        // id값이 같으면 true
        if (Objects.equals(dto.getId(), this.id)) {
            return true;
        }
        return false;
    }
}
