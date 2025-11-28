package com.example.nto.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record BookRequest(@NonNull LocalDate date, @NonNull Long placeID) {
}
