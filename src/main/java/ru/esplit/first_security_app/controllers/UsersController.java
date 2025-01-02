package ru.esplit.first_security_app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.esplit.first_security_app.security.PersonDetails;

@Controller
@RequestMapping("/user")
public class UsersController {

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String showUserInfo(Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        return "users/hello";
    }

    @GetMapping("/logout")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String showInfoForHeader(Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        return "logout";
    }
}
