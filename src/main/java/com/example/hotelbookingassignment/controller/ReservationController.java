package com.example.hotelbookingassignment.controller;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.dto.RoomDto;
import com.example.hotelbookingassignment.service.ApplicationService;
import com.example.hotelbookingassignment.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class ReservationController {
    private final ApplicationService applicationService;
    private final BookingService bookingService;
    Set<RoomDto> availableRooms = new HashSet<>();

    @GetMapping("/reservation")
    public String reservationPageBefore(Principal principal, Reservation reservation, Model model) {
        if (principal == null || principal.getName() == null) {
            return "redirect:/auth/register";
        }

        model.addAttribute("rooms", bookingService.listAllRooms());
        model.addAttribute("reservation", reservation);
        model.addAttribute("choiceDate", reservation.getReservationDate() != null);
        return "rooms";
    }

    @PostMapping("/reservation")
    public String reservationPageAfter(Principal principal, Reservation reservation, Model model) {

        var fullName = principal.getName().split(" ", 2);
        var firstName = fullName[0];
        var lastName = fullName[1];
        var guest = applicationService.registerGuest(firstName, lastName);

        if (guest.isEmpty()) {
            var availableRoom = bookingService.findAvailableRoom(reservation.getReservationDate());

            if (availableRoom.isPresent()) {
                var room = availableRoom.get();
                availableRooms = Set.of(
                        new RoomDto(
                                room.getId(),
                                room.getName(),
                                room.getSection(),
                                bookingService.isRoomAvailableAtDate(room, reservation.getReservationDate()
                                )
                        )
                );
            }
        } else {
            var allRooms = bookingService.listAllRooms();
            availableRooms = allRooms
                    .stream()
                    .map(r ->
                            new RoomDto(
                                    r.getId(),
                                    r.getName(),
                                    r.getSection(),
                                    bookingService.isRoomAvailableAtDate(r, reservation.getReservationDate()
                                    )
                            )
                    )
                    .collect(Collectors.toSet()
                    );
        }


        model.addAttribute("dateRooms", availableRooms);
        model.addAttribute("reservation", reservation);
        model.addAttribute("choiceDate", reservation.getReservationDate() != null);

        return "rooms";
    }

    @GetMapping("/reservation/booking-form")
    public String bookingForm(Principal principal,
                              @RequestParam("roomName") String roomName,
                              @RequestParam("reservationDate") LocalDate reservationDate) {
        var fullName = principal.getName().split(" ", 2);
        var firstName = fullName[0];
        var lastName = fullName[1];
        var guest = new Guest(firstName, lastName);

        applicationService.bookSpecificRoomForRegisteredGuest(guest, roomName, reservationDate);
        return "successful";
    }

    @GetMapping("/reservation/history")
    public String showReservationHistory(Principal principal, Model model) {
        if (principal == null || principal.getName() == null) {
            return "redirect:/";
        }

        var fullName = principal.getName().split(" ", 2);
        var firstName = fullName[0];
        var lastName = fullName[1];
        model.addAttribute("reservations", bookingService.findAllReservations(firstName, lastName));
        return "reservationHistory";
    }
}
