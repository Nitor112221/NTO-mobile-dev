package com.example.nto.repository;

import com.example.nto.entity.Booking;
import com.example.nto.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * TODO: ДОРАБОТАТЬ в рамках задания
 * =================================
 * МОЖНО: Добавлять методы, аннотации, зависимости
 * НЕЛЬЗЯ: Изменять название класса и пакета
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>  {
    @Query("SELECT p FROM Place p LEFT JOIN Booking b ON p.id = b.place.id AND b.date = :date " +
            "WHERE b.id IS NULL")
    List<Place> findFreePlacesByDate(@Param("date") LocalDate date);
    Optional<Booking> findByDateAndPlace(LocalDate date, Place place);
}
