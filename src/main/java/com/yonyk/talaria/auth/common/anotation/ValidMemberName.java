package com.yonyk.talaria.auth.common.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import com.yonyk.talaria.auth.common.anotation.validator.MemberNameValidator;

@Constraint(validatedBy = MemberNameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMemberName {
  String message() default "아이디를 다시 입력해주세요.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
