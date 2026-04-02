package br.com.jones.cinehub.model;

import com.fasterxml.jackson.annotation.JsonIgnore; // Importe isso!
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuario_favoritos")
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O Pulo do Gato: Ignora o usuário na hora de mandar pro Front-end!
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "filme_tmdb_id", nullable = false)
    private Long filmeTmdbId;

    @Column(nullable = false)
    private Integer posicao;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}