package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.enums.UserRole;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String adminHome(HttpSession session, Model model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem acessar esta página.");
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuarioLogado);
        return "admin-home";
    }
}
