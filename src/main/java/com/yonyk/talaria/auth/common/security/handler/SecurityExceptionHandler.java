package com.yonyk.talaria.auth.common.security.handler;

import java.io.IOException;
import java.security.SignatureException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.yonyk.talaria.auth.exception.exceptionType.SecurityExceptionType;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SecurityExceptionHandler {

  // 예외 종류 확인해서 적절한 메세지 리턴
  public String getExceptionMessage(Exception e) {
    String message = "";
    if (e instanceof JsonParseException) {
      message = SecurityExceptionType.INVALID_JSON_REQUEST.getMessage();
    } else if (e instanceof JsonMappingException) {
      message = SecurityExceptionType.INVALID_JSON_REQUEST.getMessage();
    } else if (e instanceof IOException) {
      message = SecurityExceptionType.IO_ERROR_PROCESSING_REQUEST.getMessage();
    } else if (e instanceof AuthenticationException) {
      message = SecurityExceptionType.INVALID_CREDENTIALS.getMessage();
    } else if (e instanceof MalformedJwtException) {
      message = SecurityExceptionType.INVALID_JWT_TOKEN.getMessage();
    } else if (e instanceof ExpiredJwtException) {
      message = SecurityExceptionType.EXPIRED_JWT_TOKEN.getMessage();
    } else if (e instanceof SignatureException) {
      message = SecurityExceptionType.INVALID_JWT_SIGNATURE.getMessage();
    } else if (e instanceof UnsupportedJwtException) {
      message = SecurityExceptionType.UNSUPPORTED_JWT_TOKEN.getMessage();
    } else if (e instanceof IllegalArgumentException) {
      message = SecurityExceptionType.EMPTY_JWT_CLAIMS.getMessage();
    } else if (e instanceof JwtException) {
      message = SecurityExceptionType.JWT_PROCESSING_ERROR.getMessage();
    } else if (e instanceof SecurityException) {
      message = SecurityExceptionType.GENERAL_SECURITY_ERROR.getMessage();
    } else {
      message = SecurityExceptionType.SERVER_ERROR.getMessage();
    }
    return message;
  }

  // 클라이언트에게 응답 보내기
  public void sendResponse(String message, HttpStatus httpStatus, HttpServletResponse response)
      throws IOException {
    response.setContentType(MediaType.TEXT_PLAIN_VALUE);
    response.setCharacterEncoding("UTF-8");
    response.setStatus(httpStatus.value());
    response.getWriter().write(message);
    response.getWriter().flush();
  }
}
