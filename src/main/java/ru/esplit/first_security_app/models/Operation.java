package ru.esplit.first_security_app.models;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Operation")
public class Operation implements GrantedAuthority {
    @Id
    private String id;

    @ManyToMany(mappedBy = "allowedOperations")
    private List<Role> roles;

    @Override
    public String getAuthority() {
        return id;
    }
}
