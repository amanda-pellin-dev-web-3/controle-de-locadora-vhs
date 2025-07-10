package br.edu.foz.ifpr.controle_de_locadora_vhs.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{}
