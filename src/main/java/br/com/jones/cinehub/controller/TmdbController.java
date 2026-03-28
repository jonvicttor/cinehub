package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.service.TmdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmdb")
public class TmdbController {

    @Autowired
    private TmdbService tmdbService;

    // Rota para buscar filmes: localhost:8080/api/tmdb/busca?titulo=Dracula
    @GetMapping("/busca")
    public String buscar(@RequestParam String titulo) {
        return tmdbService.buscarFilmes(titulo);
    }
}