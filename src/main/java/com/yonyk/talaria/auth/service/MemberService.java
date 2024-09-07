package com.yonyk.talaria.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yonyk.talaria.auth.controller.request.RegisterDTO;
import com.yonyk.talaria.auth.exception.CustomException;
import com.yonyk.talaria.auth.exception.exceptionType.RegisterExceptionType;
import com.yonyk.talaria.auth.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  // 회원가입 정보 검증
  public void validMember(RegisterDTO registerDTO) {
    // 계정명 중복 검증
    boolean existByMemberName = memberRepository.existsByMemberName(registerDTO.memberName());
    // 이메일 중복 검증
    boolean existByEmai = memberRepository.existsByEmail(registerDTO.email());

    // 중복일 시 예외 발생
    if (existByMemberName) throw new CustomException(RegisterExceptionType.DUPLICATED_MEMBER_NAME);
    if (existByEmai) throw new CustomException(RegisterExceptionType.DUPLICATED_EMAIL);
  }

  // 회원가입
  public void signUp(RegisterDTO registerDTO) {
    String password = bCryptPasswordEncoder.encode(registerDTO.password());
    memberRepository.save(registerDTO.toMember(password));
  }
}
