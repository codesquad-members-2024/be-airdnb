package com.airdnb.clone.global.security.constants;

public interface SecurityConstants {

    String JWT_AUTHORIZATION_HEADER = "Authorization";
    String JWT_REFRESH_TOKEN_HEADER = "Refresh-Token";
    String JWT_ACCESS_COOKIE_KEY = "jwt-token";
    String JWT_REFRESH_COOKIE_KEY = "refresh-token";
    String JWT_ISSUER = "airdnb";
    String JWT_SUBJECT = "team04";
    String USERNAME = "username";
    String AUTHORITIES = "authorities";
    String REDIRECT_URL = "http://localhost:5173";
    int JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 24;
    int REFRESH_JWT_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    int COOKIE_EXPIRATION_TIME = 10;
}
