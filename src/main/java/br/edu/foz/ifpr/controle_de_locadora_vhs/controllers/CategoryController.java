package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Category;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.enums.UserRole;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.CategoryService;
import jakarta.servlet.http.HttpSession;

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

    @GetMapping("/edit/{id}")
    public String formEditCategory(@PathVariable Long id, Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem editar categorias.");
            return "redirect:/login";
        }
        try {
            Category category = categoryService.findById(id)
                .orElseThrow(() -> new Exception("Categoria não encontrada"));
            model.addAttribute("category", category);
            model.addAttribute("isEdit", true); // Flag para indicar que é edição
            return "category-form";
        } catch (Exception e) {
            model.addAttribute("error", "Categoria não encontrada.");
            return "redirect:/category";
        }
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Category category, HttpSession session, RedirectAttributes model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem editar categorias.");
            return "redirect:/login";
        }
        
        try {
            Category existingCategory = categoryService.findById(id)
                .orElseThrow(() -> new Exception("Categoria não encontrada"));
            existingCategory.setName(category.getName());
            categoryService.save(existingCategory);
            model.addFlashAttribute("success", "Categoria atualizada com sucesso!");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao atualizar categoria: " + e.getMessage());
        }
        return "redirect:/category";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, HttpSession session, RedirectAttributes model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem excluir fitas VHS.");
            return "redirect:/login";
        }
        try {
            categoryService.deleteById(id);
            model.addFlashAttribute("success", "Categoria excluída com sucesso!");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao excluir categoria: " + e.getMessage());
        }
        return "redirect:/category";
    }
    
}
