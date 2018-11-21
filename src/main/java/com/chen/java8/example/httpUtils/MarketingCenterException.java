package com.chen.java8.example.httpUtils;


public class MarketingCenterException extends Exception {
    private static final long serialVersionUID = 31772333777495229L;
    private int errorCode;

    public MarketingCenterException(ExceptionCodeEnum enumParam) {
        super(enumParam.getDescription());
        this.errorCode = enumParam.getErrorCode();
    }

    public MarketingCenterException() {
    }

    public MarketingCenterException(String message) {
        super(message);
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}