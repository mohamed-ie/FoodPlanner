package com.soha.foodplanner.domain.exceptions;

public class UnexpectedErrorOccurredException extends Exception{
    public UnexpectedErrorOccurredException() {
        super("Unexpected error occurred please try again later");
    }
}
