package com.softee5.mobil2team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDto {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;
}
