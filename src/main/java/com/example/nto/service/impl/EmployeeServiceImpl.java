package com.example.nto.service.impl;

import com.example.nto.dto.BookingDto;
import com.example.nto.dto.EmployeeInfoDto;
import com.example.nto.entity.Booking;
import com.example.nto.entity.Employee;
import com.example.nto.exception.AuthException;
import com.example.nto.repository.EmployeeRepository;
import com.example.nto.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public Employee findByCodeOrThrow(String code) {
        return employeeRepository.findByCode(code).orElseThrow(() -> new AuthException("User not found"));
    }

    @Override
    public void checkExists(String code) {
        findByCodeOrThrow(code);
    }

    @Override
    @Transactional()
    public EmployeeInfoDto getEmployeeInfoDto(String code) {
        Employee employee = findByCodeOrThrow(code);
        Map<LocalDate, BookingDto> bookingMap = employee.getBookingList().stream()
                .collect(Collectors.toMap(
                        Booking::getDate,
                        booking -> new BookingDto(booking.getPlace().getId(), booking.getPlace().getPlace()),
                        (existing, replacement) -> existing
                ));

        return new EmployeeInfoDto(employee.getName(), employee.getPhotoUrl(), bookingMap);
    }
}
