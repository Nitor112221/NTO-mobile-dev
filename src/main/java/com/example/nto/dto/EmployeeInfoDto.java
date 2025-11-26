package com.example.nto.dto;

import java.time.LocalDate;
import java.util.Map;

public record EmployeeInfoDto(String name, String photoUrl, Map<LocalDate, BookingDto> booking) {
}
