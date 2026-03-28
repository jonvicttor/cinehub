package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.InteracaoFilme;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.InteracaoFilmeRepository;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteracaoFilmeService {

    @Autowired
    private InteracaoFilmeRepository interacaoFilmeRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Salva ou atualiza uma interação com filme
    public InteracaoFilme salvarInteracao(Long usuarioId, InteracaoFilme interacao) {
        // Busca o usuário no banco para garantir que ele existe
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Associa o usuário à interação
        interacao.setUsuario(usuario);

        // Se o usuário já tiver esse filme na lista, vamos atualizar em vez de criar um novo
        return interacaoFilmeRepository.findByUsuarioAndIdFilmeTmdb(usuario, interacao.getIdFilmeTmdb())
                .map(existente -> {
                    existente.setStatus(interacao.getStatus());
                    existente.setNota(interacao.getNota());
                    existente.setReview(interacao.getReview());
                    return interacaoFilmeRepository.save(existente);
                })
                .orElseGet(() -> interacaoFilmeRepository.save(interacao));
    }

    // Busca todos os filmes do diário de um usuário específico
    public List<InteracaoFilme> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return interacaoFilmeRepository.findByUsuario(usuario);
    }
}