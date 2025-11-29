package com.example.nto.service.impl;

import com.example.nto.repository.EmployeeRepository;
import com.example.nto.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public boolean checkAuth(String code) {
        return employeeRepository.findByCode(code).isPresent();
    }
}
