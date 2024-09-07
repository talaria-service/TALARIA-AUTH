package com.yonyk.talaria.auth.controller.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
    @NotBlank(message = "아이디를 입력해주세요.") String memberName,
    @NotBlank(message = "비밀번호를 입력해주세요.") String password) {}
