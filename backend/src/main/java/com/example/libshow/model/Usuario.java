package com.example.libshow.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import java.util.List;
import com.example.libshow.model.Emprestimo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String idUsuario;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private String matricula;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String tipo;
    // private String email;

    // Relacionamentos
    @OneToMany(mappedBy = "usuario")
    private List<Emprestimo> emprestimos;

    @OneToMany(mappedBy = "usuario")
    private List<Reserva> reservas;

}
