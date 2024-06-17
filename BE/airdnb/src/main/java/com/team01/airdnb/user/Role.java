package com.team01.airdnb.user;

public enum Role {
  USER("ROLE_USER"),
  HOST("ROLE_HOST"),
  ADMIN("ROLE_ADMIN");

  private final String key;

  Role(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }
}