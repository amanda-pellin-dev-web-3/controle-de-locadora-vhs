package br.edu.foz.ifpr.controle_de_locadora_vhs.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import br.edu.foz.ifpr.controle_de_locadora_vhs.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    public List<User> findAll(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new IllegalArgumentException("Nenhum usuário encontrado.");
        }
        return users;
    }
    
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado no sistema.");
        }
        return user;
    }

    public Optional<User> findById(Long id) {
        Optional <User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return user;
    }

    public void saveUser(User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("A senha é obrigatória.");
        }
        if (user.getPassword().length() < 3) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 3 caracteres.");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("O papel do usuário é obrigatório.");
        }
        String encryptedPassword = encoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }


}
