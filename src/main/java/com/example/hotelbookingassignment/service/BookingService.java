package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.repository.GuestRepository;
import com.example.hotelbookingassignment.repository.ReservationRepository;
import com.example.hotelbookingassignment.repository.RoomRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuestRepository guestRepository;

    public Set<Reservation> findAllReservations(String firstName, String lastName) {
        return reservationRepository.findAllByGuest_FirstNameAndGuest_LastName(firstName, lastName);
    }

    public Optional<Reservation> bookRoom(String roomName, Guest guest, LocalDate date) {
        var room = roomRepository.findByName(roomName)
                .orElseThrow(EntityNotFoundException::new);

        var existsGuest = guestRepository.findGuestByFirstNameAndLastName(guest.getFirstName(), guest.getLastName())
                .orElseThrow(EntityNotFoundException::new);

        if (reservationRepository.existsByRoomAndReservationDate(room, date)) {
            return Optional.empty();
        }

        var reservation = new Reservation(UUID.randomUUID(), room, existsGuest, date);
        reservationRepository.save(reservation);

        return Optional.of(reservation);
    }

    public Optional<Reservation> bookRoom(Room room, Guest guest, LocalDate date) {
        var existsroom = roomRepository.findByName(room.getName())
                .orElseThrow(EntityNotFoundException::new);

        var existsGuest = guestRepository.findGuestByFirstNameAndLastName(guest.getFirstName(), guest.getLastName())
                .orElseThrow(EntityNotFoundException::new);

        if (reservationRepository.existsByRoomAndReservationDate(existsroom, date)) {
            return Optional.empty();
        }

        var reservation = new Reservation(UUID.randomUUID(), room, existsGuest, date);
        reservationRepository.save(reservation);

        return Optional.of(reservation);
    }

    public Optional<Room> findAvailableRoom(LocalDate date) {
        return roomRepository.findAvailableRoom(date);
    }

    public boolean isRoomAvailableAtDate(Room room, LocalDate date) {
        var available = false;
        var existRoom = roomRepository.isRoomAvailableAtDate(room, date);
        available = existRoom.isPresent() ? false : true;
        return available;
    }

    public Set<Room> listAllRooms() {
        return roomRepository.findAll();
    }

    public Room findRoomByRoomName(String roomName) {
        return roomRepository.findByName(roomName)
                .orElseThrow(EntityExistsException::new);
    }
}
