package ru.esplit.first_security_app.services;

import java.util.List;
import java.util.Optional;

import ru.esplit.first_security_app.models.Person;
import ru.esplit.first_security_app.models.Role;

public interface AdminService {

    List<Person> getAllPeople();

    Optional<Person> getOnePerson(long id);

    void update(long id, Person updatedUser);

    void delete(long id);

    void create(Person person);

    List<Role> getAllRoles();

    void takeBackTheRole(long id, String role_id);

    void giveTheRole(long id, String role_id);

    void doAdminStaff();
}
