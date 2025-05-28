package ru.netology.common;

public enum RequestMethods {
    METHOD_GET("GET"),
    METHOD_POST("POST"),
    METHOD_PUT("PUT"),
    METHOD_DELETE("DELETE"),
    METHOD_HEAD("HEAD"),
    METHOD_PATCH("PATCH"),
    METHOD_TRACE("TRACE"),
    METHOD_CONNECT("CONNECT");
    private String method;
    RequestMethods(String method){
        this.method = method;
    }
    public String get(){
        return method;
    }
}
