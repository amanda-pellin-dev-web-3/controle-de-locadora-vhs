package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.AuthenticationService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AuthenticationController {
    
    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, RedirectAttributes model) {
        
        try{
            authenticationService.login(email, password);
            User user = authenticationService.getUserRepository().findByEmail(email).orElseThrow(() -> new Exception("Usuário não encontrado"));
            session.setAttribute("usuarioLogado", user);
            return "redirect:/vhs";
        } catch (Exception e) {
            model.addFlashAttribute("error", e.getMessage());
            return "redirect:/login";
        }
        
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
