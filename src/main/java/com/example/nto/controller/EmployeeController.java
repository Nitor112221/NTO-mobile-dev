package com.example.nto.controller;

import com.example.nto.dto.BookingDto;
import com.example.nto.dto.EmployeeInfoDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/{code}/auth")
    public ResponseEntity<Employee>  auth(@PathVariable(value = "code") String code) {
        if (!employeeRepository.findByCode(code).isPresent()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}/info")
    public ResponseEntity<EmployeeInfoDto> info(@PathVariable(value = "code") String code) {
        return employeeRepository.findByCode(code)
                .map(employee -> {
                    Map<LocalDate, BookingDto> bookingMap = employee.getBookingList().stream()
                            .collect(Collectors.toMap(
                                    Booking::getDate,
                                    booking -> new BookingDto(
                                            booking.getPlace().getId(),
                                            booking.getPlace().getPlace()
                                    ),
                                    (existing, replacement) -> existing
                            ));
                    return ResponseEntity.ok(new EmployeeInfoDto(
                            employee.getName(),
                            employee.getPhotoUrl(),
                            bookingMap
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(401).build());
    }
}
