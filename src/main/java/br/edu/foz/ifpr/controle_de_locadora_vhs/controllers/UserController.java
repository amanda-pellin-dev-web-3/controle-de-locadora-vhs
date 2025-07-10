package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public String formrRegisterUser() {
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
        
        try {
            userService.saveUser(user);
            return "redirect:/login";
        } catch (Exception exception) {
            model.addFlashAttribute("error", exception.getMessage());
            return "redirect:/register";
        }
    }
    
    
}
