package com.example.hotelbookingassignment.security;

import com.example.hotelbookingassignment.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestUserDetailsService implements UserDetailsService {
    private final GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var nameList = username.split(" ", 2);

        if (nameList.length < 2) {
            throw new UsernameNotFoundException("Invalid username format");
        }

        var firstName = nameList[0];
        var lastName = nameList[1];

        return guestRepository.findGuestByFirstNameAndLastName(firstName, lastName)
                .map(SecurityGuest::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
