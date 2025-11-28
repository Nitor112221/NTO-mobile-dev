package com.example.nto.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne(targetEntity = Place.class, fetch = FetchType.LAZY)
    private Place place;

    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.LAZY)
    private Employee employee;

    public Booking(LocalDate date, Place place, Employee employee) {
        this.date = date;
        this.place = place;
        this.employee = employee;
    }
}
