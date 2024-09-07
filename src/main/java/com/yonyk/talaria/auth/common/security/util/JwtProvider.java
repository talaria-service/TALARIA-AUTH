package com.yonyk.talaria.auth.common.security.util;

import java.util.Date;

import javax.crypto.SecretKey;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.yonyk.talaria.auth.common.security.details.PrincipalDetailsService;
import com.yonyk.talaria.auth.common.security.record.JwtRecord;
import com.yonyk.talaria.auth.entity.Member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private final PrincipalDetailsService principalDetailsService;

  // jwt 시크릿 키
  @Value("${jwt.secret}")
  String secretKey;

  // 액세스 토큰이 저장될 헤더 필드 이름
  @Value("${jwt.access-token-header}")
  public String accessTokenHeader;

  // 액세스 토큰 앞에 지정될 프리픽스
  @Value("${jwt.prefix}")
  public String prefix;

  // 액세스 토큰 만료 기한
  @Value("${jwt.access-token-TTL}")
  private int accessTokenTTL;

  // 리프레시 토큰 만료 기한
  @Value("${jwt.refresh-token-TTL}")
  private int refreshTokenTTL;

  // jwt 파싱 암호키
  private SecretKey key;

  // 객체 생성 후 초기화
  @PostConstruct
  public void init() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    this.key = Keys.hmacShaKeyFor(keyBytes);
  }

  // 로그인 성공 시 액세스 토큰, 리프레시 토큰 발급
  public JwtRecord getLoginToken(Authentication authResult) {
    String authorities =
        authResult.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("권한이 없는 사용자"));
    String memberName = authResult.getName();
    return generateJwt(authorities, memberName);
  }

  // 재발급 시 액세스 토큰, 리프레시 토큰 발급
  public JwtRecord getReissueToken(Member member) {
    String authorities = member.getMemberRole().getRole();
    String memberName = member.getMemberName();
    return generateJwt(authorities, memberName);
  }

  // 액세스 토큰, 리프레시 토큰이 담긴 JwtRecord 생성 메소드
  public JwtRecord generateJwt(String authorities, String memberName) {
    long now = (new Date()).getTime();

    // 액세스 토큰 생성
    String accessToken =
        Jwts.builder()
            .subject(memberName)
            .claim("authorities", authorities)
            .expiration(new Date(now + (accessTokenTTL * 1000L)))
            .signWith(key)
            .compact();
    // 리프레시 토큰 생성
    String refreshToken =
        Jwts.builder()
            .subject(memberName)
            .expiration(new Date(now + (refreshTokenTTL * 1000L)))
            .signWith(key)
            .compact();

    return new JwtRecord(accessToken, refreshToken);
  }

  // 액세스 토큰 가져오기
  public String getAccessToken(HttpServletRequest request) {
    // 헤더에서 토큰 가져오기
    return request.getHeader(accessTokenHeader);
  }

  // 인증 객체 리턴
  public Authentication getAuthentication(String token) {
    // accessToken에서 prefix 제거
    String accessToken = token.substring(prefix.length());
    // 액세스 토큰 해석하여 정보 가져오기
    Claims claims =
        Jwts.parser().verifyWith(key).build().parseSignedClaims(accessToken).getPayload();
    // 액세스 토큰에 담긴 memberId로 PrincipalDetails 객체 가져오기
    UserDetails userDetails = principalDetailsService.loadUserByUsername(claims.getSubject());
    // 가져온 객체를 이용해 인증객체 만들기
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }
}
