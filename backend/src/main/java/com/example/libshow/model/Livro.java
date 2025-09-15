package com.example.libshow.model;

import com.example.libshow.enums.LivroStatus;
import jakarta.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;

    private String titulo;
    private String autor;
    private int ano;

    @Enumerated(EnumType.STRING)
    private LivroStatus livroStatus;

    // Relações
    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "livro")
    private List<Emprestimo> emprestimos;

    @OneToMany(mappedBy = "livro")
    private List<Reserva> reservas;


    // Métodos de negócio
    public boolean emprestarLivro() {
        if (this.livroStatus == LivroStatus.DISPONIVEL) {
            this.livroStatus = LivroStatus.EMPRESTADO;
            return true;
        }
        return false;
    }

    public boolean devolverLivro() {
        if (this.livroStatus == LivroStatus.EMPRESTADO || this.livroStatus == LivroStatus.RESERVADO) {
            this.livroStatus = LivroStatus.DISPONIVEL;
            return true;
        }
        return false;
    }

    public boolean reservarLivro() {
        if (this.livroStatus == LivroStatus.DISPONIVEL) {
            this.livroStatus = LivroStatus.RESERVADO;
            return true;
        }
        return false;
    }

    public boolean cancelarReserva() {
        if (this.livroStatus == LivroStatus.RESERVADO) {
            this.livroStatus = LivroStatus.DISPONIVEL;
            return true;
        }
        return false;
    }

    public boolean isEmprestado() {
        return this.livroStatus == LivroStatus.EMPRESTADO;
    }
}