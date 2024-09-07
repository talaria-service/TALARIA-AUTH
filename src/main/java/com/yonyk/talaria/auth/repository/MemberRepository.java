package com.yonyk.talaria.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yonyk.talaria.auth.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  // 로그인 시 memberName으로 사용자 반환
  Optional<Member> findByMemberName(String memberName);

  // 계정명 중복 검사
  boolean existsByMemberName(String memberName);

  // 이메일 중복 검사
  boolean existsByEmail(String email);
}
