package ru.esplit.first_security_app.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.repositories.PeopleRepository;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        peopleRepository.save(person);
    }
}