package com.practice.practiceProject.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
  CUSTOMER("ROLE_CUSTOMER"),
  SHOP_OWNER("ROLE_SHOP_OWNER");

  private final String value;

  RoleEnum(String value) {
    this.value = value;
  }

}
