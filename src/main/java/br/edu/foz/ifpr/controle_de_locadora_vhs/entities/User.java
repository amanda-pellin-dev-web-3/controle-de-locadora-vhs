package br.edu.foz.ifpr.controle_de_locadora_vhs.entities;

import java.util.ArrayList;
import java.util.List;

import br.edu.foz.ifpr.controle_de_locadora_vhs.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Loan> loans = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
