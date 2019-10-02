package com.marketpro.user.security;

public class SecurityConstants {
    static final String SIGN_UP_URLS="/api/authenticate/create-user";
    static final String LOGIN_URLS="/api/authenticate/login";
    static final String FORGOT_PASSWORD_URLS="/api/authenticate/forgot-password";
    static final String Admin_URLS="/admin";

    public static final String SECRET_KEY="SecretkeytoJWT";
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final long EXPIRATION_TIME=30_000; //30 seconds
}
