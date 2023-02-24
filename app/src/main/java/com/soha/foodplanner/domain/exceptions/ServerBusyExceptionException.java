package com.soha.foodplanner.domain.exceptions;

public class ServerBusyExceptionException extends Exception{
    public ServerBusyExceptionException() {
        super("Server is busy please try again later");
    }
}
