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

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<InteracaoFilme> salvar(@PathVariable Long usuarioId, @RequestBody InteracaoFilme interacao) {
        try {
            InteracaoFilme salva = interacaoFilmeService.salvarInteracao(usuarioId, interacao);
            return ResponseEntity.ok(salva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<InteracaoFilme>> listar(@PathVariable Long usuarioId) {
        List<InteracaoFilme> lista = interacaoFilmeService.listarPorUsuario(usuarioId);
        return ResponseEntity.ok(lista);
    }

    // NOVA ROTA: Rota para deletar um filme do diário
    @DeleteMapping("/usuario/{usuarioId}/filme/{idFilmeTmdb}")
    public ResponseEntity<Void> deletar(@PathVariable Long usuarioId, @PathVariable Long idFilmeTmdb) {
        try {
            interacaoFilmeService.deletarInteracao(usuarioId, idFilmeTmdb);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}