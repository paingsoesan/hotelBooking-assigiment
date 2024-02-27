package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private GuestRegistrationService guestRegistrationService;

    @Autowired
    private BookingService bookingService;

    public Optional<Guest> registerGuest(String firstName, String lastName) {
        return guestRegistrationService.registerGuest(new Guest(firstName, lastName));
    }

    public void bookSpecificRoomForRegisteredGuest(Guest guest, String roomName, LocalDate date) {
        var reservation = bookingService.bookRoom(roomName, guest, date)
                .orElseThrow(EntityNotFoundException::new);
    }
}
