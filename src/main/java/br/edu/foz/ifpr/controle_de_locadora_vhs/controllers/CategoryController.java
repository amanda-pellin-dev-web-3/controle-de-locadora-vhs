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
    
    @GetMapping("/novo")
    public String formNewCategory() {
        return "category-form";
    }

    @PostMapping("/novo")
    public String registerCategory(@RequestParam String name, RedirectAttributes model) {
        Category category = new Category();
        category.setName(name);
        try {
            categoryService.save(category);
            model.addFlashAttribute("success", "Categoria cadastrada com sucesso!");
            return "redirect:/category";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao cadastrar categoria: " + e.getMessage());
            return "redirect:/category/novo";
        }
    }
    
}
