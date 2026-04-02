package br.com.jones.cinehub.repository;

import br.com.jones.cinehub.model.Convite;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConviteRepository extends JpaRepository<Convite, Long> {

    // Busca os convites que estão tocando o "sininho" do usuário
    List<Convite> findByDestinatarioIdAndStatusOrderByCreatedAtDesc(Long destinatarioId, String status);

    // Busca os filmes que vocês já aceitaram assistir juntos (Sessão Dupla)
    List<Convite> findByDestinatarioIdAndStatusOrRemetenteIdAndStatus(Long destinatarioId, String status1, Long remetenteId, String status2);

    // Impede de mandar 10 convites do mesmo filme para a mesma pessoa
    boolean existsByRemetenteIdAndDestinatarioIdAndFilmeTmdbId(Long remetenteId, Long destinatarioId, Long filmeTmdbId);
}