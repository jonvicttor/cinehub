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

    // Relacionamento: Várias interações de filmes pertencem a um único usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Salvamos apenas o ID oficial da API do TMDB (O front-end vai puxar a capa e o título de lá)
    @Column(name = "id_filme_tmdb", nullable = false)
    private Long idFilmeTmdb;

    // Salva no banco como texto (QUERO_VER, JA_VI, etc)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusFilme status;

    // Nota que o usuário vai dar (ex: 1 a 5 estrelas ou 0 a 100)
    private Integer nota;

    // columnDefinition = "TEXT" permite salvar textos longos, ideal para a sua review do Letterboxd
    @Column(columnDefinition = "TEXT")
    private String review;

}