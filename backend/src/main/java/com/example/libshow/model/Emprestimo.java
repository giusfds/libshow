package com.example.libshow.model;

import com.example.libshow.enums.EmprestimoStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "emprestimos")
public class Emprestimo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    @Enumerated(EnumType.STRING) // Salva o enum como string no banco
    private EmprestimoStatus emprestimoStatus;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Emprestimo() {}

    // Métodos de Negócio
    public void calcularDataDevolucao(int diasParaDevolver) {
        if (this.dataEmprestimo != null) {
            this.dataDevolucao = this.dataEmprestimo.plusDays(diasParaDevolver);
        }
    }

    public boolean verificarAtraso() {
        return this.emprestimoStatus == EmprestimoStatus.ATIVO && this.dataDevolucao != null && LocalDate.now().isAfter(this.dataDevolucao);
    }

    public void registrarDevolucao() {
        this.emprestimoStatus = EmprestimoStatus.DEVOLVIDO;
    }
}
