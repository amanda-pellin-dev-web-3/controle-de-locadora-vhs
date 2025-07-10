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
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.VHS;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.VHSService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/vhs")
public class VHSController {
    
    @Autowired
    VHSService vhsService;


    @GetMapping()
    public String findAll(Model model) {
        List<VHS> vhsList = vhsService.findAll();
        model.addAttribute("vhsList", vhsList);
        return "vhs-list";
    }

    @GetMapping("/novo")
    public String formNewVHS(Model model){
        model.addAttribute("tapeStatusList", TapeStatus.values());
        //model.addAttribute("categories", categoryService.findAll());

        return "vhs-form";
    }

    @PostMapping("/novo")
    public String registerVHS(
        @RequestParam String title, 
        @RequestParam(required = false) String imageUrl, 
        @RequestParam String director, 
        @RequestParam LocalDate registrationDate, 
        @RequestParam Category category,
        @RequestParam TapeStatus tapeStatus,
        RedirectAttributes model) {
            
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
            return "redirect:/vhs/novo";
        }
    }
    
    
}
