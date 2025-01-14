package com.anthive.blogservice.accountsystem.base.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountRegisterRequest {

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{4,20}$",
            message = "영문과 숫자, 일부 특수문자(._-)만 사용가능, 4-20자 길이. 영문 포함 필수.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문 대,소문자와 숫자, 특수기호를 적어도 1개 이상씩 포함하고 8자 ~ 20자여야 합니다.")
    private String password;
}
