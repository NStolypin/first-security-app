package ru.esplit.first_security_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.esplit.first_security_app.models.Person;

public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
