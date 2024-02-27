package com.example.hotelbookingassignment;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Reservation;
import com.example.hotelbookingassignment.ds.Role;
import com.example.hotelbookingassignment.ds.Room;
import com.example.hotelbookingassignment.repository.GuestRepository;
import com.example.hotelbookingassignment.repository.ReservationRepository;
import com.example.hotelbookingassignment.repository.RoleRepository;
import com.example.hotelbookingassignment.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class HotelBookingAssignmentApplication {
    private final RoomRepository roomRepository;
    private final RoleRepository roleRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;

    @Bean
    @Profile("data")
    public ApplicationRunner runner() {
        return r -> {
            Room room1 = new Room(UUID.randomUUID(), "HSD-210", "Standard");
            Room room2 = new Room(UUID.randomUUID(), "HOC-230", "Ocean View");
            Room room3 = new Room(UUID.randomUUID(), "HFS-212", "Family Suite");
            Room room4 = new Room(UUID.randomUUID(), "HSU-233", "Suite");
            Room room5 = new Room(UUID.randomUUID(), "HAC-212", "Accessible");
            Room room6 = new Room(UUID.randomUUID(), "HPH-210", "Penthouse");

            roomRepository.save(room1);
            roomRepository.save(room2);
            roomRepository.save(room3);
            roomRepository.save(room4);
            roomRepository.save(room5);
            roomRepository.save(room6);
        };
    }

    @Bean
    @Profile("data")
    public ApplicationRunner runner2() {
        return r -> {
            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");

            roleRepository.save(role1);
            roleRepository.save(role2);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingAssignmentApplication.class, args);
    }

}
