package br.edu.foz.ifpr.controle_de_locadora_vhs.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.VHS;
import br.edu.foz.ifpr.controle_de_locadora_vhs.repositories.VHSRepository;

@Service
public class VHSService {
    @Autowired
    VHSRepository vhsRepository;

    public List<VHS> findAll() {
        return vhsRepository.findAll();
    }

    public Optional<VHS> findById(Long id) {
        return vhsRepository.findById(id);
    }

    public VHS save(VHS vhs) {
        if (vhs.getTitle() == null || vhs.getTitle().isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }
        if (vhs.getDirector() == null || vhs.getDirector().isBlank()) {
            throw new IllegalArgumentException("O diretor é obrigatório.");
        }
        if (vhs.getRegistrationDate() == null) {
            throw new IllegalArgumentException("A data de registro é obrigatória.");
        }
        if (vhs.getCategory() == null) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        if (vhs.getStatus() == null) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }
        return vhsRepository.save(vhs);
    }
    public void deleteById(Long id) {
        if (!vhsRepository.existsById(id)) {
            throw new IllegalArgumentException("Fita VHS não encontrada.");
        }
        vhsRepository.deleteById(id);    
    }
}
