package com.example.hotelbookingassignment.repository;

import com.example.hotelbookingassignment.ds.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {

    Set<Room> findAll();

    Optional<Room> findByName(String name);

    @Query("""
    select distinct r from Room r
    where (
    select count (s) from r.reservations s
    where s.reservationDate = :date
    ) = 0
    order by r.id
    limit 1
    """)
    Optional<Room> findAvailableRoom(LocalDate date);

    @Query("""
    select r from Room r
    join r.reservations s
    where r = :room 
    and
    s.reservationDate = :date
    """)
    Optional<Room> isRoomAvailableAtDate(Room room, LocalDate date);
}
