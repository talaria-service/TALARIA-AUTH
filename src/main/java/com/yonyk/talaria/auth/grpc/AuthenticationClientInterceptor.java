package com.yonyk.talaria.auth.grpc;

import org.springframework.stereotype.Component;

import io.grpc.*;

@Component
public class AuthenticationClientInterceptor implements ClientInterceptor {
  private Metadata metadata;

  public void setInterceptor(String accessTokenHeader, String accessToken) {
    this.metadata = new Metadata();
    Metadata.Key<String> key = Metadata.Key.of(accessTokenHeader, Metadata.ASCII_STRING_MARSHALLER);
    metadata.put(key, accessToken);
  }

  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(
      MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(
        channel.newCall(methodDescriptor, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        headers.merge(metadata);
        super.start(responseListener, headers);
      }
    };
  }
}
