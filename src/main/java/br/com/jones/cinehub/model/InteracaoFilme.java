package br.com.jones.cinehub.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "interacoes_filmes")
public class InteracaoFilme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "id_filme_tmdb", nullable = false)
    private Long idFilmeTmdb;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFilme status;

    private Double nota;

    @Column(columnDefinition = "TEXT")
    private String review;

    // NOVOS CAMPOS: Tags e Registro de Idioma
    private String tags; // Ex: "Plot Twist, Clássico"
    private String idiomaAudio; // Ex: "Dublado", "Legendado", "Original"

}