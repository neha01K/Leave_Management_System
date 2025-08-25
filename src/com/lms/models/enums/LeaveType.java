package com.lms.models.enums;

public enum LeaveType {
    CASUAL_LEAVE(10), EARNED_LEAVE(0), SICK_LEAVE(12),
    DUTY_LEAVE(0),MATERNITY_LEAVE(120),
    PARENTAL_LEAVE(7), LEAVE_WITHOUT_PAY(180);

        private final int yearlyAllocation;
        LeaveType(int yearlyAllocation) {
            this.yearlyAllocation = yearlyAllocation;
        }

        public int getYearlyAllocation() {
            return yearlyAllocation;
        }
}
