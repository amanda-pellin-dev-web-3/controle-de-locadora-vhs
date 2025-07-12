package br.edu.foz.ifpr.controle_de_locadora_vhs.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Category;
import br.edu.foz.ifpr.controle_de_locadora_vhs.repositories.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }
    
    public Category save(Category category) {
        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        return categoryRepository.save(category);
    }
    
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Categoria não encontrada.");
        }
        categoryRepository.deleteById(id);
    }
}
