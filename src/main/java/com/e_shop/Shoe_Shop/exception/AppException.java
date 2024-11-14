package com.e_shop.Shoe_Shop.exception;


public class AppException extends RuntimeException{
    private ErrorCode errorCode;

    public AppException(final ErrorCode errorCode) {
      this.errorCode = errorCode;
    }

    public AppException() {
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
