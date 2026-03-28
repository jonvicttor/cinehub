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

    public InteracaoFilme salvarInteracao(Long usuarioId, InteracaoFilme interacao) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        interacao.setUsuario(usuario);

        return interacaoFilmeRepository.findByUsuarioAndIdFilmeTmdb(usuario, interacao.getIdFilmeTmdb())
                .map(existente -> {
                    existente.setStatus(interacao.getStatus());
                    existente.setNota(interacao.getNota());
                    existente.setReview(interacao.getReview());
                    // ATUALIZAÇÃO: Novos campos incluídos no Upsert
                    existente.setTags(interacao.getTags());
                    existente.setIdiomaAudio(interacao.getIdiomaAudio());
                    return interacaoFilmeRepository.save(existente);
                })
                .orElseGet(() -> interacaoFilmeRepository.save(interacao));
    }

    public List<InteracaoFilme> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return interacaoFilmeRepository.findByUsuario(usuario);
    }

    public void deletarInteracao(Long usuarioId, Long idFilmeTmdb) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        InteracaoFilme interacao = interacaoFilmeRepository.findByUsuarioAndIdFilmeTmdb(usuario, idFilmeTmdb)
                .orElseThrow(() -> new RuntimeException("Interação não encontrada no diário"));

        interacaoFilmeRepository.delete(interacao);
    }
}