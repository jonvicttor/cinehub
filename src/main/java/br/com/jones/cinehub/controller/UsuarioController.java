package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Diz pro Spring que essa classe responde requisições Web (APIs REST)
@RequestMapping("/usuarios") // O endereço base no navegador/ferramenta será localhost:8080/usuarios
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Rota para CRIAR um novo usuário (POST)
    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
            // Retorna o código 201 (Created) e os dados do usuário salvo
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (RuntimeException e) {
            // Se cair naquela regra de email/nickname repetido do Service, dá erro 400 (Bad Request)
            return ResponseEntity.badRequest().build();
        }
    }

    // Rota para BUSCAR um usuário pelo ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario); // Retorna 200 (OK)
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 (Not Found)
        }
    }
}