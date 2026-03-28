package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.Amizade;
import br.com.jones.cinehub.model.StatusAmizade;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.AmizadeRepository;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Enviar um pedido de amizade
    public Amizade enviarPedido(Long solicitanteId, Long recebedorId) {
        Usuario solicitante = usuarioRepository.findById(solicitanteId)
                .orElseThrow(() -> new RuntimeException("Solicitante não encontrado"));
        Usuario recebedor = usuarioRepository.findById(recebedorId)
                .orElseThrow(() -> new RuntimeException("Recebedor não encontrado"));

        Amizade amizade = new Amizade();
        amizade.setSolicitante(solicitante);
        amizade.setRecebedor(recebedor);
        amizade.setStatus(StatusAmizade.PENDENTE);

        return amizadeRepository.save(amizade);
    }

    // Aceitar ou Recusar um pedido
    public Amizade responderPedido(Long amizadeId, StatusAmizade novoStatus) {
        Amizade amizade = amizadeRepository.findById(amizadeId)
                .orElseThrow(() -> new RuntimeException("Pedido de amizade não encontrado"));

        amizade.setStatus(novoStatus);
        return amizadeRepository.save(amizade);
    }

    // Listar amizades de um usuário
    public List<Amizade> listarAmizades(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return amizadeRepository.findBySolicitanteOrRecebedor(usuario, usuario);
    }
}