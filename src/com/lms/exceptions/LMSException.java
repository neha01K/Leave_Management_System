package com.lms.exceptions;

public class LMSException extends Exception{
    private String errorCode;

    public LMSException(String message){
        super(message);
    }

    public LMSException(String message, Throwable cause){
        super(message, cause);
    }

    public LMSException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

    public LMSException(String errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode(){
        return errorCode;
    }
}
