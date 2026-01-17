package org.triangle.security.restapi.constant;

public final class SecurityConstant {
    private SecurityConstant(){

    }
    public static final String API_KEY_HEADER="X-API-KEY";
    public static final String API_KEY_ENV = "API_KEY";

    public static final String UNAUTHORIZED_MESSAGE = "Invalid or missing API Key";
}
