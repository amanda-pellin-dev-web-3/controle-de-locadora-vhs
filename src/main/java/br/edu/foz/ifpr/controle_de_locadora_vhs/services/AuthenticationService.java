package br.edu.foz.ifpr.controle_de_locadora_vhs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.repositories.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    UserRepository userRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User login(String email, String senha ){

        User user = userRepository.findByEmail(email).get();

        if (user == null || !encoder.matches(senha, user.getPassword())) {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }

        return user; 

    }
}
