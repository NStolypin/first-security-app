package ru.esplit.first_security_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.esplit.first_security_app.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
}
