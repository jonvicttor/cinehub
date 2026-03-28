package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.InteracaoFilme;
import br.com.jones.cinehub.service.InteracaoFilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes")
public class InteracaoFilmeController {

    @Autowired
    private InteracaoFilmeService interacaoFilmeService;

    // Rota para salvar uma interação (Nota, Review, Status)
    // O ID do usuário vem na URL para sabermos de quem é o diário
    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<InteracaoFilme> salvar(@PathVariable Long usuarioId, @RequestBody InteracaoFilme interacao) {
        try {
            InteracaoFilme salva = interacaoFilmeService.salvarInteracao(usuarioId, interacao);
            return ResponseEntity.ok(salva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Rota para listar todos os filmes do diário de um usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InteracaoFilme>> listar(@PathVariable Long usuarioId) {
        List<InteracaoFilme> lista = interacaoFilmeService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }
}