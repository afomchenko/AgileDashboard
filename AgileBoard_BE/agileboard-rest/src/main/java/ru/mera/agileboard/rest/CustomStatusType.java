package ru.mera.agileboard.rest;

import javax.ws.rs.core.Response.Status;

public class CustomStatusType extends AbstractStatusType {


    public CustomStatusType(Status httpStatus, String message) {
        super(httpStatus, message);
    }

}