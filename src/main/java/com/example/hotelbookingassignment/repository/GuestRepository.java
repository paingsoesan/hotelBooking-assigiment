package com.example.hotelbookingassignment.repository;

import com.example.hotelbookingassignment.ds.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface GuestRepository extends CrudRepository<Guest, UUID> {
    public Optional<Guest> findGuestByFirstNameAndLastName(String firstName, String lastName);

    @Query("""
    select g from Guest g
    join g.reservations s
    where g.firstName = :firstName
    and
    g.lastName = :lastName
    order by g.id
    limit 1
    """)
    public Optional<Guest> registerGuest(String firstName, String lastName);
}
