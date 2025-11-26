package com.example.nto.controller;

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

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/{code}/auth")
    public ResponseEntity<Employee>  auth(@PathVariable(value = "code") String code, Model model) {
        if (!employeeRepository.findByCode(code).isPresent()) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}/info")
    public ResponseEntity<Map<String, Object>> info(@PathVariable(value = "code") String code, Model model) {
        if (!employeeRepository.findByCode(code).isPresent()) {
            return ResponseEntity.status(401).build();
        }

        Employee employee = employeeRepository.findByCode(code).get();

        Map<String, Object> response = new HashMap<>();
        response.put("name", employee.getName());
        response.put("photoUrl", employee.getPhotoUrl());
        Map<LocalDate, Object> bookings = new HashMap<>();
        for (Booking booking : employee.getBookingList()) {
            Map<String, Object> place = new HashMap<>();
            place.put("id", booking.getPlace().getId());
            place.put("place", booking.getPlace().getPlace());
            bookings.put(booking.getDate(), place);
        }
        response.put("booking", bookings);
        return ResponseEntity.ok().body(response);
    }
}
