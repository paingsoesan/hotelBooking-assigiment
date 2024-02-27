package com.example.hotelbookingassignment.ds;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"room_id", "reservationDate"})})
public class Reservation {
    @Id
    @GeneratedValue
    UUID id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    private Guest guest;

    private LocalDate reservationDate;

    public Reservation(UUID id, Room room, Guest guest, LocalDate reservationDate) {
        this.id = id;
        this.room = room;
        this.guest = guest;
        this.reservationDate = reservationDate;
    }
}