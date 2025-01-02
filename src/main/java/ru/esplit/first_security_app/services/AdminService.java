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
public class AdminService {

    private final PeopleRepository peopleRepository;
    private final RoleRepository roleRepository;

    public AdminService(PeopleRepository peopleRepository, 
            RoleRepository roleRepository) {
        this.peopleRepository = peopleRepository;
        this.roleRepository = roleRepository;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public Optional<Person> getOnePerson(long id) {
        return peopleRepository.findById(id);
    }
    
    @Transactional
    public void update(long id, Person updatedUser){
        Optional<Person> personForUpdated = peopleRepository.findById(id);
        if(personForUpdated.isPresent()) {
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
        peopleRepository.save(person);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void doAdminStaff() {
        System.out.println("Only admin hear");
    }


}
