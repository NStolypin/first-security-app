package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;
import ru.esplit.first_security_app.repositories.PeopleRepository;
import ru.esplit.first_security_app.repositories.RoleRepository;

@Transactional(readOnly = true)
@Service
public class AdminServiceImpl implements AdminService {

    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;
    private RegistrationService registrationService;

    public AdminServiceImpl(PeopleRepository peopleRepository,
            RoleRepository roleRepository,
            RegistrationService registrationService) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;
        this.registrationService = registrationService;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getOnePerson(long id) {
        return peopleRepository.findById(id);
    }

    @Transactional
    public void update(long id, Person updatedUser) {
        Optional<Person> personForUpdated = getOnePerson(id);
        if (personForUpdated.isPresent()) {
            personForUpdated.get().setUsername(updatedUser.getUsername());
            personForUpdated.get().setYearOfBirth(updatedUser.getYearOfBirth());
            peopleRepository.save(personForUpdated.get());
        }
    }

    @Transactional
    public void delete(long id) {
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void create(Person person) {
        registrationService.register(person);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional
    public void takeBackTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (personO.get().getRoles().contains(roleO.get())) {
                personO.get().getRoles().remove(roleO.get());
            }
        }
    }

    @Transactional
    public void giveTheRole(long id, String role_id) {
        Optional<Role> roleO = roleRepository.findById(role_id);
        Optional<Person> personO = getOnePerson(id);
        if (personO.isPresent() & roleO.isPresent()) {
            if (!personO.get().getRoles().contains(roleO.get())) {
                List<Role> lr = (List<Role>) personO.get().getRoles();
                lr.add(roleO.get());
            }
        }
    }

    public void doAdminStaff() {
        System.out.println("Only admin hear");
    }

}
