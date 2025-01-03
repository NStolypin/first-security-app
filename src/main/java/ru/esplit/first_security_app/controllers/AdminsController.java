package ru.esplit.first_security_app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.validation.Valid;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.security.PersonDetails;
import ru.esplit.first_security_app.services.AdminService;
import ru.esplit.first_security_app.services.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminsController {

    private final AdminService adminService;
    private final RoleService roleService;

    public AdminsController(AdminService adminService,
            RoleService roleService) {
        this.adminService = adminService;
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage(@ModelAttribute("person") Person person, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("people", adminService.getAllPeople());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/hello";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showAllUsers(Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("people", adminService.getAllPeople());
        return "admins/get_all_users";
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showOneUser(@PathVariable("id") long id, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("person", adminService.getOnePerson(id));
        return "admins/get_one_user";
    }

    @GetMapping("/users/{id}/edit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable("id") long id) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("person", adminService.getOnePerson(id));
        return "admins/edit";
    }

    @PostMapping("/users/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String update(@ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult, @PathVariable("id") long id, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.getAllRoles());
            return "admins/edit";
        }
        adminService.update(id, person);
        return "redirect:/admin";
    }

    @PostMapping("/users/{id}/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@PathVariable("id") long id, Model model) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        adminService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/users/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String newPerson(@ModelAttribute("person") Person person, Model model) {
        return "admins/new";
    }

    @PostMapping("/users/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String create(@ModelAttribute("person") @Valid Person person, 
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("personDetails", personDetails.getPerson());
            return "admins/new";
        }
        adminService.create(person);
        return "redirect:/admin";
    }

    @GetMapping("/users/{id}/editrole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editRole(Model model, @PathVariable("id") long id) {
        PersonDetails personDetails = (PersonDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("personDetails", personDetails.getPerson());
        model.addAttribute("person", adminService.getOnePerson(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admins/get_all_roles";
    }

    @PostMapping("/users/{id}/addrole/{role_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String giveTheRole(@PathVariable("id") long id, @PathVariable("role_id") String role_id) {
        roleService.giveTheRole(id, role_id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/deleterole/{role_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String takeBackTheRole(@PathVariable("id") long id, @PathVariable("role_id") String role_id) {
        roleService.takeBackTheRole(id, role_id);
        return "redirect:/admin/users";
    }
}
