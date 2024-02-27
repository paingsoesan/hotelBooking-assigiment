package com.example.hotelbookingassignment.service;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.ds.Role;
import com.example.hotelbookingassignment.repository.GuestRepository;
import com.example.hotelbookingassignment.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestRegistrationService {
    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<Guest> registerGuest(Guest guest) {
        return guestRepository.registerGuest(guest.getFirstName(), guest.getLastName());
    }

    public void saveGuest(Guest guest) {
        Role role = roleRepository.findRoleByRoleName("ROLE_USER")
                .orElseThrow(EntityNotFoundException::new);

        guest.setPassword(passwordEncoder.encode(guest.getPassword()));
        guest.setRole(role);

        guestRepository.save(guest);
    }

    public Optional<Guest> findGuestByFirstNameAndLastName(String firstName, String lastName) {
        return guestRepository.findGuestByFirstNameAndLastName(firstName, lastName);
    }
}
