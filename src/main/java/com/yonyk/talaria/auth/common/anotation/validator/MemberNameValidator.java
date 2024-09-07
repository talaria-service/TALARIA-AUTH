package com.yonyk.talaria.auth.common.anotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.yonyk.talaria.auth.common.anotation.ValidMemberName;

public class MemberNameValidator implements ConstraintValidator<ValidMemberName, String> {

  // 아이디는 영문과 숫자만 포함 가능
  private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]+$";

  @Override
  public boolean isValid(String memberName, ConstraintValidatorContext context) {

    // 아이디가 8자 이상인지 확인
    if (memberName.length() < 9) {
      context
          .buildConstraintViolationWithTemplate("아이디는 8자 이상이어야합니다.")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }

    // 아이디가 영문, 숫자로만 이루어져있는지 확인
    if (!memberName.matches(PASSWORD_REGEX)) {
      context
          .buildConstraintViolationWithTemplate("아이디는 영문과 숫자로 이루어져있어야합니다.")
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }
    return true;
  }
}
