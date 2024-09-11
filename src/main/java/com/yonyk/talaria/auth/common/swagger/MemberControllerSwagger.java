package com.yonyk.talaria.auth.common.swagger;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.yonyk.talaria.auth.controller.request.RegisterDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Member", description = "사용자 관련 API입니다.")
public interface MemberControllerSwagger {

  @Operation(summary = "회원가입", description = "사용자 회원가입 때 사용되는 API")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "회원가입이 성공적으로 완료되었습니다.",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "409",
            description = "이미 사용 중인 계정/이메일입니다.",
            content = @Content(mediaType = "application/json"))
      })
  public ResponseEntity<String> signUp(@Valid @RequestBody RegisterDTO registerDTO);

  @Operation(
      summary = "refreshToken 재발급",
      description = "refreshToken으로 accessToken, refreshToken 재발급하는 API")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "토큰 재발급이 성공적으로 완료되었습니다.",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(
            responseCode = "404",
            description =
                "지정된 쿠키를 찾을 수 없습니다. 또는 저장된 refreshToken을 찾을 수 없습니다. 또는 해당하는 아이디의 회원이 없습니다.",
            content = @Content(mediaType = "application/json"))
      })
  public ResponseEntity<String> reissueRefreshToken(
      HttpServletRequest request, HttpServletResponse response);
}
