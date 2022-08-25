package com.sparta.hanghaeboardproject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardDto {
    private String title;
    private String writer;
    private String contents;
    private String password;
    private String comment;
}
