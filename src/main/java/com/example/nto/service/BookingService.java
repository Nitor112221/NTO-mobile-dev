package com.example.nto.service;

import com.example.nto.dto.BookRequest;
import com.example.nto.dto.PlaceDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@Service
public interface BookingService {
    Map<LocalDate, List<PlaceDto>> getAvailablePlaces(String code);
    void bookPlace(String code, BookRequest request);
}
