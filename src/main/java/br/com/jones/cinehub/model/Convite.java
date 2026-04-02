package br.com.jones.cinehub.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "convites_filmes")
public class Convite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem enviou o convite
    @ManyToOne
    @JoinColumn(name = "remetente_id", nullable = false)
    @JsonIgnoreProperties({"senha", "email", "bio", "seguidores", "seguindo"})
    private Usuario remetente;

    // Quem vai receber o convite
    @ManyToOne
    @JoinColumn(name = "destinatario_id", nullable = false)
    @JsonIgnoreProperties({"senha", "email", "bio", "seguidores", "seguindo"})
    private Usuario destinatario;

    @Column(name = "filme_tmdb_id", nullable = false)
    private Long filmeTmdbId;

    @Column(nullable = false)
    private String status = "PENDENTE"; // PENDENTE, ACEITO, RECUSADO

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // ==========================================
    // GETTERS E SETTERS EXPLÍCITOS
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public Long getFilmeTmdbId() {
        return filmeTmdbId;
    }

    public void setFilmeTmdbId(Long filmeTmdbId) {
        this.filmeTmdbId = filmeTmdbId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}