package br.edu.foz.ifpr.controle_de_locadora_vhs.services;

import java.util.List;
import java.util.Optional;

import br.edu.foz.ifpr.controle_de_locadora_vhs.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Loan;

@Service
public class LoanService {

   @Autowired
   LoanRepository loanRepository;

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan save(Loan loan) {
        if (loan.getDataEmprestimo() == null) {
            throw new IllegalArgumentException("A data de empréstimo é obrigatória.");
        }
        if (loan.getDataDevolucao() == null) {
            throw new IllegalArgumentException("A data de devolução é obrigatória.");
        }
        if (loan.getVhsList() == null || loan.getVhsList().isEmpty()) {
            throw new IllegalArgumentException("A lista de VHS é obrigatória.");
        }
        if (loan.getUser() == null) {
            throw new IllegalArgumentException("O usuário é obrigatório.");
        }
        return loanRepository.save(loan);
    }
}
