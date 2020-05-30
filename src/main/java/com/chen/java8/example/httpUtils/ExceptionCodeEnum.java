package com.chen.java8.example.httpUtils;

public enum ExceptionCodeEnum {
    EXAMPLE(1,"例子")
    ;

    private Integer errorCode;

    private String description;


    ExceptionCodeEnum(Integer errorCode, String description) {
        this.description = description;
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return description;
    }
}
