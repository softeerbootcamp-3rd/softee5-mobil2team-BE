package com.softee5.mobil2team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {
    private Long id;
    private String nickname;
    private Date createdTime;
    private String Content;
    private String imageUrl;
    private Long tagId;
    private Integer like;
}
