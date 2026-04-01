package br.com.jones.cinehub.repository;

import br.com.jones.cinehub.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {

    // Busca os favoritos de um usuário já ordenados pela posição (1 a 6)
    List<Favorito> findByUsuarioIdOrderByPosicaoAsc(Long usuarioId);

    // Apaga todos os favoritos de um usuário de uma vez só (útil para a atualização em massa)
    @Transactional
    void deleteByUsuarioId(Long usuarioId);
}