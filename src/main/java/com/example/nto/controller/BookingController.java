package com.example.nto.controller;

import com.example.nto.dto.BookRequest;
import com.example.nto.dto.PlaceDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Place;
import com.example.nto.repository.BookingRepository;
import com.example.nto.repository.EmployeeRepository;
import com.example.nto.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    private PlaceRepository placeRepository;

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

    @PostMapping("/{code}/book")
    public ResponseEntity<Object> book(
            @PathVariable(value = "code") String code,
            @RequestBody() BookRequest bookRequest
            ) {
        LocalDate date = bookRequest.date();
        return employeeRepository.findByCode(code).map(
                employee -> {
                    Optional<Place> place = placeRepository.findById(bookRequest.placeID());
                    if (place.isEmpty()) {
                        return ResponseEntity.status(401).build();
                    }

                    Optional<Booking> booking = bookingRepository.findByDateAndPlace(date, place.get());

                    if (booking.isPresent()) {
                        return ResponseEntity.status(409).build();
                    }

                    Booking new_booking = new Booking(date, place.get(), employee);
                    bookingRepository.save(new_booking);
                    return ResponseEntity.status(201).build();

                }).orElseGet(() -> ResponseEntity.status(401).build());
    }
}
