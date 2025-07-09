package br.edu.foz.ifpr.controle_de_locadora_vhs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     public Optional<User> findByEmail(String email);
}
