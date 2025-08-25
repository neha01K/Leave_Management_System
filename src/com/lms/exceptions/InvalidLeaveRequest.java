package com.lms.exceptions;

public class InvalidLeaveRequest extends LMSException{
    public InvalidLeaveRequest(String message){
        super(message);
    }

    public InvalidLeaveRequest(String message, Throwable cause){
        super(message,cause);
    }
}
