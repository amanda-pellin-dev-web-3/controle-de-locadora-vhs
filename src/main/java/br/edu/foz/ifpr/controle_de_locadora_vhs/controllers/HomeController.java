package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping({"", "/", "/home"})
    public String home(HttpSession session, Model model) {
        User usuario = (User) session.getAttribute("usuarioLogado");
        model.addAttribute("usuario", usuario);
        return "home";
    }
}
