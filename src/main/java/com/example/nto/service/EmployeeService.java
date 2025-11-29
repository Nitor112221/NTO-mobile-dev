package com.example.nto.service;

import org.springframework.stereotype.Service;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@Service
public interface EmployeeService {
    boolean checkAuth(String code);
}
