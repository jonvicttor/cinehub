package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioSalvo = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> dados) {
        try {
            Usuario usuario = usuarioService.fazerLogin(dados.get("email"), dados.get("senha"));
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/busca/{nickname}")
    public ResponseEntity<Usuario> buscarPorNickname(@PathVariable String nickname) {
        try {
            Usuario usuario = usuarioService.buscarPorNickname(nickname);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/seguir/{seguidoId}")
    public ResponseEntity<Void> seguir(@PathVariable Long id, @PathVariable Long seguidoId) {
        try {
            usuarioService.seguirOuDeixarDeSeguir(id, seguidoId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}/seguindo")
    public ResponseEntity<Set<Usuario>> listarSeguindo(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.listarQuemEuSigo(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/tema")
    public ResponseEntity<Void> atualizarTema(@PathVariable Long id, @RequestBody Map<String, String> dados) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            usuario.setCorTema(dados.get("cor"));
            usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // NOVO: Rota para atualizar os dados gerais do perfil
    @PutMapping("/{id}/perfil")
    public ResponseEntity<Usuario> atualizarPerfil(@PathVariable Long id, @RequestBody Map<String, String> dados) {
        try {
            Usuario usuario = usuarioService.buscarPorId(id);
            if (dados.containsKey("nome")) usuario.setNome(dados.get("nome"));
            if (dados.containsKey("nickname")) usuario.setNickname(dados.get("nickname"));
            if (dados.containsKey("bio")) usuario.setBio(dados.get("bio"));
            if (dados.containsKey("fotoUrl")) usuario.setFotoUrl(dados.get("fotoUrl"));

            Usuario usuarioAtualizado = usuarioService.cadastrarUsuario(usuario);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}