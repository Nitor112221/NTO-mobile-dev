package com.example.nto.controller;

import com.example.nto.dto.PlaceDto;
import com.example.nto.repository.BookingRepository;
import com.example.nto.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/{code}/booking")
    public ResponseEntity<Map<LocalDate, List<PlaceDto>>> booking(@PathVariable(value = "code") String code) {
        return employeeRepository.findByCode(code).map(
                employee -> {
                    Map<LocalDate, List<PlaceDto>> placeDtoMap = new HashMap<>();
                    LocalDate date = LocalDate.now();
                    for (int i = 0; i < 4; i++) {
                        placeDtoMap.put(date, bookingRepository.findFreePlacesByDate(date).stream()
                                .map(place -> new PlaceDto(
                                        place.getId(),
                                        place.getPlace()
                                ))
                                .collect(Collectors.toList()));
                        date = date.plusDays(1);
                    }

                    return ResponseEntity.ok(placeDtoMap);
                }
        ).orElseGet(() -> ResponseEntity.status(401).build());
    }
}
