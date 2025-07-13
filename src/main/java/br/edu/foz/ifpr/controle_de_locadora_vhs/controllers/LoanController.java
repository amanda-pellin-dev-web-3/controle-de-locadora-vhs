package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.Loan;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.VHS;
import br.edu.foz.ifpr.controle_de_locadora_vhs.enums.TapeStatus;
import br.edu.foz.ifpr.controle_de_locadora_vhs.enums.UserRole;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.LoanService;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.UserService;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.VHSService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    VHSService vhsService;

    @Autowired
    UserService userService;

    @GetMapping()
    public String listLoans(Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem acessar esta página.");
            return "redirect:/login";
        }
        model.addAttribute("loanList", loanService.findAll());
        return "loan-list";
    }

    @GetMapping("/new")
    public String formNewLoan(Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem cadastrar empréstimos.");
            return "redirect:/login";
        }
        List<VHS> vhsDisponiveis = vhsService.findAll().stream()
            .filter(vhs -> vhs.getStatus() == TapeStatus.DISPONÍVEL)
            .toList();
        model.addAttribute("vhsList", vhsDisponiveis);
        model.addAttribute("users", userService.findAll());
        return "loan-form";
    }

    @PostMapping("/new")
    public String registerLoan(
            @RequestParam List<Long> vhsIds,
            @RequestParam Long userId,
            @RequestParam LocalDate dataEmprestimo,
            @RequestParam LocalDate dataDevolucao,
            RedirectAttributes model,
            HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem cadastrar empréstimos.");
            return "redirect:/login";
        }
        try {
            Loan loan = new Loan();
            loan.setDevolvido(false); // Definir explicitamente como false
            
            List<VHS> vhsList = vhsService.findAll().stream()
                    .filter(vhs -> vhsIds.contains(vhs.getId()))
                    .toList();

            for (VHS vhs : vhsList) {
                if (vhs.getStatus() != TapeStatus.DISPONÍVEL) {
                    model.addFlashAttribute("error",
                            "A fita '" + vhs.getTitle() + "' não está disponível para empréstimo.");
                    return "redirect:/loan/new";
                }
            }
            
            User user = userService.findById(userId).orElseThrow(() -> new Exception("Usuário não encontrado"));
            loan.setVhsList(vhsList);
            loan.setUser(user);
            loan.setDataEmprestimo(dataEmprestimo);
            loan.setDataDevolucao(dataDevolucao);
            loanService.save(loan);
            
            for (VHS vhs : vhsList) {
                vhs.setStatus(TapeStatus.EMPRESTADA);
                vhsService.save(vhs);
            }

            model.addFlashAttribute("success", "Empréstimo cadastrado com sucesso!");
            return "redirect:/loan";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao cadastrar empréstimo: " + e.getMessage());
            return "redirect:/admin";
        }
    }
    @PostMapping("/return/{id}")
    public String returnLoan(@PathVariable Long id, HttpSession session, RedirectAttributes model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem devolver empréstimos.");
            return "redirect:/login";
        }
        
        try {
            Loan loan = loanService.findById(id)
                .orElseThrow(() -> new Exception("Empréstimo não encontrado"));
            
            // Alterar status das fitas para DISPONÍVEL
            for (VHS vhs : loan.getVhsList()) {
                vhs.setStatus(TapeStatus.DISPONÍVEL);
                vhsService.save(vhs);
            }
            
            loan.setDevolvido(true);
            loanService.save(loan);
            
            model.addFlashAttribute("success", "Empréstimo devolvido com sucesso!");
            return "redirect:/loan";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao devolver empréstimo: " + e.getMessage());
            return "redirect:/loan";
        }
    }
    @GetMapping("/edit/{id}")
    public String editLoan(@PathVariable Long id, Model model, HttpSession session) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addAttribute("error", "Apenas administradores podem editar empréstimos.");
            return "redirect:/login";
        }
        try{
            Loan loan = loanService.findById(id)
                .orElseThrow(() -> new Exception("Empréstimo não encontrado"));
            model.addAttribute("loan", loan);
            model.addAttribute("vhsList", vhsService.findAll());
            model.addAttribute("users", userService.findAll());
            model.addAttribute("isEdit", true); 
            return "loan-form";
        }
        catch (Exception e) {
            model.addAttribute("error", "Erro ao buscar empréstimo: " + e.getMessage());
            return "redirect:/loan";
        }
    }
    @PostMapping("/edit/{id}")
    public String updateLoan(@PathVariable Long id, 
                            @RequestParam LocalDate dataDevolucao,
                            HttpSession session, 
                            RedirectAttributes model) {
        User usuarioLogado = (User) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null || usuarioLogado.getRole() != UserRole.ADMIN) {
            model.addFlashAttribute("error", "Apenas administradores podem editar empréstimos.");
            return "redirect:/login";
        }
        try {
            Loan existingLoan = loanService.findById(id)
                    .orElseThrow(() -> new Exception("Empréstimo não encontrado"));
            existingLoan.setDataDevolucao(dataDevolucao);
            loanService.save(existingLoan);
            model.addFlashAttribute("success", "Empréstimo atualizado com sucesso!");
            return "redirect:/loan";
        } catch (Exception e) {
            model.addFlashAttribute("error", "Erro ao atualizar empréstimo: " + e.getMessage());
            return "redirect:/loan/edit/" + id;
        }
    }
}
