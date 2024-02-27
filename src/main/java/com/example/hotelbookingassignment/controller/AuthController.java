package com.example.hotelbookingassignment.controller;

import com.example.hotelbookingassignment.ds.Guest;
import com.example.hotelbookingassignment.service.GuestRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final GuestRegistrationService guestRegistrationService;

    @GetMapping("/auth/register")
    public String registerForm(Model model) {
        model.addAttribute("guest", new Guest());
        return "registerForm";
    }

    @PostMapping("/auth/save-guest")
    public String createGuest(Model model, Guest guest, BindingResult result) {
        model.addAttribute("guest",new Guest());
        var id = UUID.randomUUID();
        guest.setId(id);

        var existGuest = guestRegistrationService
                .findGuestByFirstNameAndLastName(guest.getFirstName(), guest.getLastName());

        if (result.hasErrors() || existGuest.isPresent()) {
            model.addAttribute("existGuest", true);
            return "registerForm";
        }

        guestRegistrationService.saveGuest(guest);
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/auth/loging")
    public String authenticateGuest(Guest guest, BindingResult result) {
        if (result.hasErrors()) {
            return "loginForm";
        }

        return "redirect:/";
    }

    @GetMapping("/auth/error-login")
    public String errorLogin(Model model) {
        model.addAttribute("loginError", true);
        return "loginForm";
    }
}
