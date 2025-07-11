package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.UserRole;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.UserService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String formrRegisterUser(Model model) {
        model.addAttribute("userRoles", UserRole.values());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
        @RequestParam String name, 
        @RequestParam String email, 
        @RequestParam String password,
        RedirectAttributes model) {
        
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        
        if (userService.findAll().isEmpty()){
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.CLIENTE);
        }

        try {
            userService.saveUser(user);
            model.addFlashAttribute("success", "Usuário cadastrado com sucesso!");
            return "redirect:/register";
        } catch (Exception exception) {
            model.addFlashAttribute("error", exception.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("edit/{id}")
    public String updateUser(Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem acessar esta página.");
            return "redirect:/login";
        }
        model.addAttribute("userRoles", UserRole.values());
        return "user-edit";
    }
    
    @PostMapping("/edit/{id}")
    public String promoteToAdmin(@PathVariable Long id, HttpSession session, RedirectAttributes model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem promover usuários.");
            return "redirect:/login";
        }
        try {
            User user = userService.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
            user.setRole(UserRole.ADMIN);
            userService.saveUser(user);
            model.addFlashAttribute("success", "Usuário promovido a administrador!");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao promover usuário: " + e.getMessage());
        }
        return "redirect:/usuarios";
    }
    
}
