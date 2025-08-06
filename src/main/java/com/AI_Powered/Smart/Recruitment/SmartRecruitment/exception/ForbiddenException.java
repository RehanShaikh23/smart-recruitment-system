package com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String notYourJob) {
        super(notYourJob);
    }
}
