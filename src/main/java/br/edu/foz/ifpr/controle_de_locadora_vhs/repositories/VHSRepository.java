package br.edu.foz.ifpr.controle_de_locadora_vhs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.VHS;

@Repository
public interface VHSRepository extends JpaRepository<VHS, Long> {}
