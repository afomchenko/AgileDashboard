package ru.mera.agileboard.rest;

/**
 * Created by antfom on 13.03.2015.
 */
public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException(String message) {
        super(message);
    }

}
