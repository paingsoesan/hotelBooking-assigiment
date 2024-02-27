package com.example.hotelbookingassignment.controller;

import com.example.hotelbookingassignment.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final RoomRepository roomRepository;

    @RequestMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("rooms", roomRepository.findAll());
        return "index";
    }

    @GetMapping("/contact")
    public String contactForm() {
        return "contact";
    }

    @PostMapping("/contact/subscribe")
    public String subscribe(@RequestParam("email") String email) {
        System.out.println(email);
        return "redirect:/contact";
    }
}
