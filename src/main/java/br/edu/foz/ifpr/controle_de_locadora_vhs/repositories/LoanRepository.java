package br.edu.foz.ifpr.controle_de_locadora_vhs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {}
