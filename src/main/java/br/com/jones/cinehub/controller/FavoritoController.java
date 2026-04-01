package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.Favorito;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.FavoritoRepository;
import br.com.jones.cinehub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioService usuarioService;

    // Retorna a lista de favoritos na ordem correta
    @GetMapping
    public ResponseEntity<List<Favorito>> buscarFavoritos(@PathVariable Long usuarioId) {
        List<Favorito> favoritos = favoritoRepository.findByUsuarioIdOrderByPosicaoAsc(usuarioId);
        return ResponseEntity.ok(favoritos);
    }

    // Recebe uma lista de IDs do TMDB (Ex: [550, 120, 300]) e recria o Top 6
    @PostMapping
    public ResponseEntity<Void> atualizarTop6(@PathVariable Long usuarioId, @RequestBody List<Long> tmdbIds) {
        try {
            Usuario usuario = usuarioService.buscarPorId(usuarioId);

            // 1. Limpa a lista antiga inteira
            favoritoRepository.deleteByUsuarioId(usuarioId);

            // 2. Limita a 6 filmes por segurança
            int max = Math.min(tmdbIds.size(), 6);
            List<Favorito> novosFavoritos = new ArrayList<>();

            // 3. Salva a nova lista com as posições atualizadas (1 a 6)
            for (int i = 0; i < max; i++) {
                Favorito fav = new Favorito();
                fav.setUsuario(usuario);
                fav.setFilmeTmdbId(tmdbIds.get(i));
                fav.setPosicao(i + 1); // A posição começa no 1
                novosFavoritos.add(fav);
            }

            favoritoRepository.saveAll(novosFavoritos);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}