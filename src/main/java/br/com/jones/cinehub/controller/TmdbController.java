package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.FilmeDTO;
import br.com.jones.cinehub.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    // Rota para buscar filmes/séries pelo nome
    @GetMapping("/busca")
    public List<FilmeDTO> buscar(@RequestParam String titulo) {
        return tmdbService.buscarFilmes(titulo);
    }

    // NOVA ROTA: Buscar os detalhes exatos de um filme/série pelo ID
    // Exemplo de uso no front: localhost:8080/api/tmdb/12345
    @GetMapping("/{id}")
    public FilmeDTO buscarPorId(@PathVariable Long id) {
        return tmdbService.buscarPorId(id);
    }
}