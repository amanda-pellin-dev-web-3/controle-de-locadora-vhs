package br.edu.foz.ifpr.controle_de_locadora_vhs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.edu.foz.ifpr.controle_de_locadora_vhs.services.LoanService;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.UserService;
import br.edu.foz.ifpr.controle_de_locadora_vhs.services.VHSService;

@Controller
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    VHSService vhsService;
    
    @Autowired
    UserService userService;
}
