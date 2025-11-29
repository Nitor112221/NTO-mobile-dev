package com.example.nto.controller;

import com.example.nto.dto.BookRequest;
import com.example.nto.dto.PlaceDto;
import com.example.nto.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    BookingService bookingService;

    @GetMapping("/{code}/booking")
    public ResponseEntity<Map<LocalDate, List<PlaceDto>>> booking(@PathVariable(value = "code") String code) {
        return ResponseEntity.ok(bookingService.getAvailablePlaces(code));
    }

    @PostMapping("/{code}/book")
    public ResponseEntity<Object> book(
            @PathVariable(value = "code") String code,
            @RequestBody() BookRequest bookRequest
            ) {
        bookingService.bookPlace(code, bookRequest);
        return ResponseEntity.status(201).build();
    }
}
