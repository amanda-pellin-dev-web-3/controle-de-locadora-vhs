package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Category;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.TapeStatus;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.UserRole;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.VHS;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.CategoryService;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.VHSService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/vhs")
public class VHSController {
    
    @Autowired
    VHSService vhsService;

    @Autowired
    CategoryService categoryService;

    @GetMapping()
    public String findAll(Model model) {
        List<VHS> vhsList = vhsService.findAll();
        model.addAttribute("vhsList", vhsList);
        return "vhs-list";
    }

    @GetMapping("/new")
    public String formNewVHS(Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem acessar esta página.");
            return "redirect:/login";
        }
        model.addAttribute("tapeStatusList", TapeStatus.values());
        model.addAttribute("categories", categoryService.findAll());

        return "vhs-form";
    }

    @PostMapping("/new")
    public String registerVHS(
        @RequestParam String title, 
        @RequestParam(required = false) String imageUrl, 
        @RequestParam String director, 
        @RequestParam LocalDate registrationDate, 
        @RequestParam Category category,
        @RequestParam TapeStatus tapeStatus,
        RedirectAttributes model,
        HttpSession session) {
        
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem cadastrar fitas VHS.");
            return "redirect:/login";
        }
        VHS vhs = new VHS();
        vhs.setTitle(title);
        vhs.setImageUrl(imageUrl);
        vhs.setDirector(director);
        vhs.setRegistrationDate(registrationDate);
        vhs.setCategory(category);
        vhs.setStatus(tapeStatus);
        try {
            vhsService.save(vhs);
            model.addFlashAttribute("success", "VHS cadastrado com sucesso!");
            return "redirect:/vhs";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao cadastrar VHS: " + e.getMessage());
            return "redirect:/vhs/new";
        }
    }
    
    @GetMapping("/edit/{id}")
    public String formUpdateVHS(@PathVariable Long id, Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem acessar esta página.");
            return "redirect:/login";
        }
        List<VHS> vhsList = vhsService.findAll();
        for (VHS vhs : vhsList) {
            if (vhs.getId().equals(id)) {
                model.addAttribute("vhs", vhs);
                model.addAttribute("tapeStatusList", TapeStatus.values());
                // model.addAttribute("categories", categoryService.findAll());
                return "vhs-form";
            }
        }
        return "redirect:/vhs";
    }

    @PostMapping("/edit/{id}")
    public String updateVHS(VHS vhs, @PathVariable Long id, RedirectAttributes model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem editar as fitas VHS.");
            return "redirect:/login";
        }
        try {
            VHS existingVHS = vhsService.findById(id).orElseThrow(() -> new Exception("VHS not found"));
            existingVHS.setTitle(vhs.getTitle());
            existingVHS.setImageUrl(vhs.getImageUrl());
            existingVHS.setDirector(vhs.getDirector());
            existingVHS.setRegistrationDate(vhs.getRegistrationDate());
            existingVHS.setCategory(vhs.getCategory());
            existingVHS.setStatus(vhs.getStatus());
            vhsService.save(existingVHS);
            model.addFlashAttribute("success", "VHS atualizado com sucesso!");
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao atualizar VHS: " + e.getMessage());
        }
        return "redirect:/vhs";
    }

}
