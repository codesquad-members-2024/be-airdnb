package com.team01.airdnb.authorization.jwt.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConstants {

    @Value("${jwt.secret}")
    private String keyProperty;
    @Value("${jwt.access-exp-time}")
    private int accessExpTimeProperty;
    @Value("${jwt.refresh-exp-time}")
    private int refreshExpTimeProperty;
    @Value("${jwt.header}")
    private String jwtHeaderProperty;
    @Value("${jwt.type}")
    private String jwtTypeProperty;

    public static String key;
    public static int ACCESS_EXP_TIME;
    public static int REFRESH_EXP_TIME;
    public static String JWT_HEADER;
    public static String JWT_TYPE;

    @PostConstruct
    public void init() {
        key = keyProperty;
        ACCESS_EXP_TIME = accessExpTimeProperty;
        REFRESH_EXP_TIME = refreshExpTimeProperty;
        JWT_HEADER = jwtHeaderProperty;
        JWT_TYPE = jwtTypeProperty;
    }
}
