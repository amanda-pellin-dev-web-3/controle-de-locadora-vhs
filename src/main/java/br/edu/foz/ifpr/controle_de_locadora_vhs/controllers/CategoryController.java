package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Category;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public String findAll(Model model) {
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "category-list";
    }
    
    @GetMapping("/new")
    public String formNewCategory() {
        return "category-form";
    }

    @PostMapping("/new")
    public String registerCategory(@RequestParam String name, RedirectAttributes model) {
        Category category = new Category();
        category.setName(name);
        try {
            categoryService.save(category);
            model.addFlashAttribute("success", "Categoria cadastrada com sucesso!");
            return "redirect:/category";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao cadastrar categoria: " + e.getMessage());
            return "redirect:/category/new";
        }
    }

    @GetMapping("/editar/{id}")
    public String formEditCategory(@PathVariable Long id, Model model) {
        List<Category> categoryList = categoryService.findAll();
        for (Category category : categoryList) {
            if (category.getId().equals(id)) {
                model.addAttribute("category", category);
                return "category-form";
            }
        }
        return "redirect:/category";
    }

    @PostMapping("/editar/{id}")
    public String editCategory(Category category, RedirectAttributes model) {
        try {
            Category existingCategory = categoryService.findById(category.getId())
                .orElseThrow(() -> new Exception("Categoria não encontrada"));
            existingCategory.setName(category.getName());
            categoryService.save(existingCategory);
            model.addFlashAttribute("success", "Categoria atualizada com sucesso!");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao atualizar categoria: " + e.getMessage());
        }
        return "redirect:/category";
    }

    
}
