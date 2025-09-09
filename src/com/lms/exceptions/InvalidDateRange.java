package com.lms.exceptions;


import java.time.LocalDate;

public class InvalidDateRange extends LMSException{

    private LocalDate startDate;
    private LocalDate endDate;
    public InvalidDateRange(LocalDate startDate, LocalDate endDate){
        super(String.format("Invalid Date Range: Start Date %s is before End Date %s", startDate, endDate));
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public InvalidDateRange(String message){
        super(message);
    }

    public InvalidDateRange(String message, Throwable cause){
        super(message, cause);
    }

    public LocalDate getStartDate(){
        return startDate;
    }
    public LocalDate getEndDate(){
        return endDate;
    }
}
