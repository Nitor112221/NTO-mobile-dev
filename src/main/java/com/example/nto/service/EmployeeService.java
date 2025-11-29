package com.example.nto.service;

import com.example.nto.dto.EmployeeInfoDto;
import com.example.nto.entity.Employee;
import org.springframework.stereotype.Service;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@Service
public interface EmployeeService {
    void checkExists(String code);
    Employee findByCodeOrThrow(String code);
    EmployeeInfoDto getEmployeeInfoDto(String code);
}
