package com.yonyk.talaria.auth.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyk.talaria.auth.controller.request.RegisterDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

  // 회원가입 API
  @PostMapping
  public ResponseEntity<String> signUp(@Valid @RequestBody RegisterDTO registerDTO) {
    return null;
  }
}
