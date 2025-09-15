package com.example.libshow.model;

import com.example.libshow.enums.ReservaStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    private LocalDate dataReserva;

    @Enumerated(EnumType.STRING) // Salva o enum como string no banco
    private ReservaStatus reservaStatus;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    public Reserva() {}


    // Métodos de Negócio
    public void realizarReserva() {
        this.reservaStatus = ReservaStatus.PENDENTE;
        this.dataReserva = LocalDate.now();
    }

    public void cancelarReserva() {
        this.reservaStatus = ReservaStatus.CANCELADA;
    }
    
    public boolean validarTempoLimite(int diasLimite) {
        if (this.reservaStatus == ReservaStatus.PENDENTE && this.dataReserva != null) {
            return LocalDate.now().isBefore(this.dataReserva.plusDays(diasLimite));
        }
        return false;
    }
}
