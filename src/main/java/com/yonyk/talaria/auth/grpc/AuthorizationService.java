package com.yonyk.talaria.auth.grpc;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class AuthorizationService extends AuthorizationServiceGrpc.AuthorizationServiceImplBase {
  @Override
  public void getAuthentication(
      AuthorizationProto.AuthRequest request,
      StreamObserver<AuthorizationProto.AuthResponse> responseObserver) {

    // 스프링 시큐리티에 등록된 인증객체 가져오기
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // 사용자 아이디 가져오기
    String memberName = authentication.getName();
    // 사용자 권한 가져오기
    List<String> memberRoles =
        authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

    // 응답 객체 생성 및 전송
    AuthorizationProto.AuthResponse response =
        AuthorizationProto.AuthResponse.newBuilder()
            .setMemberName(memberName)
            .addAllMemberRole(memberRoles)
            .build();

    // 클라이언트에게 응답 전송
    responseObserver.onNext(response);
    // 응답 전송 끝 알림
    responseObserver.onCompleted();
  }
}
