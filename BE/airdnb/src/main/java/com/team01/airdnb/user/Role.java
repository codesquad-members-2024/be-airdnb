package com.team01.airdnb.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  USER("ROLE_USER"),
  HOST("ROLE_HOST"),
  ADMIN("ROLE_ADMIN");

  private final String value;
}