package com.sparta.hanghaeboardproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    @Length(min = 4, max = 12)
    @Pattern(regexp = "[a-zA-Z\\d]*${4,12}")
    private String username;

    @NotBlank
    @Length(min = 4, max = 32)
    @Pattern(regexp = "[a-z\\d]*${4,32}")
    private String password;

    @NotBlank
    private  String passwordConfirm;

    private boolean admin = false;
    private String adminToken = "";
}
