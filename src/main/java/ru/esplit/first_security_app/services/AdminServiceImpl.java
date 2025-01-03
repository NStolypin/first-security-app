package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.repositories.PeopleRepository;

@Transactional(readOnly = true)
@Service
public class AdminServiceImpl implements AdminService {

    private final PeopleRepository peopleRepository;
    private final RegistrationService registrationService;
    private final PasswordEncoder passwordEncoder;

    public AdminServiceImpl(PeopleRepository peopleRepository,
            RegistrationService registrationService,
            PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.registrationService = registrationService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    @Override
    public Optional<Person> getOnePerson(long id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    @Override
    public void update(long id, Person updatedUser) {
        Optional<Person> personForUpdated = getOnePerson(id);
        if (personForUpdated.isPresent()) {
            updatedUser.setId(id);
            peopleRepository.save(updatedUser);
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void create(Person person) {
        registrationService.register(person);
    }

    @Override
    public void doAdminStaff() {
        System.out.println("Only admin hear");
    }

}
