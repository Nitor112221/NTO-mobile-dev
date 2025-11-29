package com.example.nto.service.impl;

import com.example.nto.dto.BookRequest;
import com.example.nto.dto.PlaceDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.entity.Place;
import com.example.nto.exception.PlaceAlreadyBookedException;
import com.example.nto.exception.PlaceNotFoundException;
import com.example.nto.repository.BookingRepository;
import com.example.nto.repository.PlaceRepository;
import com.example.nto.service.BookingService;
import com.example.nto.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;

    private final EmployeeService employeeService;

    private final PlaceRepository placeRepository;

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<PlaceDto>> getAvailablePlaces(String code) {
        employeeService.checkExists(code);

        Map<LocalDate, List<PlaceDto>> result = new HashMap<>();
        LocalDate date = LocalDate.now();
        for (int i = 0; i < 4; i++) {
            result.put(date, bookingRepository.findFreePlacesByDate(date).stream()
                    .map(place -> new PlaceDto(place.getId(), place.getPlace()))
                    .collect(Collectors.toList()));
            date = date.plusDays(1);
        }
        return result;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void bookPlace(String code, BookRequest request) {
        Employee employee = employeeService.findByCodeOrThrow(code);
        LocalDate date = request.date();
        Place place = placeRepository.findById(request.placeID()).orElseThrow(
                () -> new PlaceNotFoundException("Place not found")
        );
        bookingRepository.findByDateAndPlace(date, place).orElseThrow(
                () -> new PlaceAlreadyBookedException("Place already booked")
        );

        Booking booking = new Booking(date, place, employee);
        bookingRepository.save(booking);
    }
}
