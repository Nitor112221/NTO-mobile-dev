package com.example.nto.exception;

public class PlaceAlreadyBookedException extends RuntimeException {
    public PlaceAlreadyBookedException(String message) { super(message); }
}
