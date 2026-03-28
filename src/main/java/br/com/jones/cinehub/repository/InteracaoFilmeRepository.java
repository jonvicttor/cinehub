package br.com.jones.cinehub.repository;

import br.com.jones.cinehub.model.InteracaoFilme;
import br.com.jones.cinehub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteracaoFilmeRepository extends JpaRepository<InteracaoFilme, Long> {

    // Busca todos os filmes na lista de um usuário específico
    List<InteracaoFilme> findByUsuario(Usuario usuario);

    // Busca para ver se o usuário já adicionou um filme específico do TMDB na lista dele
    Optional<InteracaoFilme> findByUsuarioAndIdFilmeTmdb(Usuario usuario, Long idFilmeTmdb);
}