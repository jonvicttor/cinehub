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

    @GetMapping("/busca")
    public List<FilmeDTO> buscar(@RequestParam String titulo) {
        return tmdbService.buscarFilmes(titulo);
    }

    // NOVA ROTA: Carrega as listas prontas (Populares, Awards, etc)
    @GetMapping("/lista")
    public List<FilmeDTO> buscarLista(@RequestParam String cat, @RequestParam(defaultValue = "movie") String tipo) {
        return tmdbService.buscarLista(cat, tipo);
    }

    @GetMapping("/{id}")
    public FilmeDTO buscarPorId(@PathVariable Long id) {
        return tmdbService.buscarPorId(id);
    }
}