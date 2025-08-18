package com.lms.exceptions;

public class InvalidLeaveRequest extends LMSException{
    public InvalidLeaveRequest(String message){
        super("LEAVE_001",message);
    }

    public InvalidLeaveRequest(String message, Throwable cause){
        super("LEAVE_101",message,cause);
    }
}
