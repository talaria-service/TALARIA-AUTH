package com.yonyk.talaria.auth.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import com.yonyk.talaria.auth.common.anotation.ValidMemberName;
import com.yonyk.talaria.auth.common.anotation.ValidPassword;
import com.yonyk.talaria.auth.entity.Member;
import com.yonyk.talaria.auth.entity.enums.MemberRole;

public record RegisterDTO(
    @NotBlank(message = "아이디를 입력해주세요.") @ValidMemberName String memberName,
    @NotBlank(message = "비밀번호를 입력해주세요.") @ValidPassword String password,
    @NotBlank(message = "이메일을 입력해주세요.") @Email(message = "올바른 이메일 형식이 아닙니다.") String email) {

  public Member toMember(String password) {
    return Member.builder()
        .memberName(this.memberName)
        .password(password)
        .email(this.email)
        .memberRole(MemberRole.USER)
        .build();
  }
}
