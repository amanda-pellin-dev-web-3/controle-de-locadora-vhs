package br.edu.foz.ifpr.controle_de_locadora_vhs.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    private String name;

    @OneToMany(mappedBy="category")
    @ToString.Exclude
    private List<VHS> vhsList = new ArrayList<>();

}
