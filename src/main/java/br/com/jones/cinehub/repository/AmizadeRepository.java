package br.com.jones.cinehub.repository;

import br.com.jones.cinehub.model.Amizade;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.model.StatusAmizade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, Long> {

    // Traz todas as amizades onde o usuário foi quem enviou OU quem recebeu o convite
    List<Amizade> findBySolicitanteOrRecebedor(Usuario solicitante, Usuario recebedor);

    // Traz apenas os convites de amizade que o usuário recebeu e estão com status PENDENTE
    List<Amizade> findByRecebedorAndStatus(Usuario recebedor, StatusAmizade status);
}