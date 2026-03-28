package br.com.jones.cinehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "amizades")
public class Amizade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem enviou o pedido de amizade
    @ManyToOne
    @JoinColumn(name = "solicitante_id", nullable = false)
    private Usuario solicitante;

    // Quem recebeu o pedido de amizade
    @ManyToOne
    @JoinColumn(name = "recebedor_id", nullable = false)
    private Usuario recebedor;

    // Status (PENDENTE, ACEITA, RECUSADA)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAmizade status;

}