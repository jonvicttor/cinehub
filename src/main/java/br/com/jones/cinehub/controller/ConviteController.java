package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.Convite;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.ConviteRepository;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/convites")
public class ConviteController {

    @Autowired
    private ConviteRepository conviteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. O clique de enviar convite
    @PostMapping("/enviar")
    public ResponseEntity<?> enviarConvite(@RequestBody Map<String, Object> payload) {
        Long remetenteId = Long.valueOf(payload.get("remetenteId").toString());
        Long destinatarioId = Long.valueOf(payload.get("destinatarioId").toString());
        Long filmeTmdbId = Long.valueOf(payload.get("filmeTmdbId").toString());

        if (conviteRepository.existsByRemetenteIdAndDestinatarioIdAndFilmeTmdbId(remetenteId, destinatarioId, filmeTmdbId)) {
            return ResponseEntity.badRequest().body("Você já enviou um convite deste filme para esta pessoa.");
        }

        Usuario remetente = usuarioRepository.findById(remetenteId).orElse(null);
        Usuario destinatario = usuarioRepository.findById(destinatarioId).orElse(null);

        if (remetente == null || destinatario == null) {
            return ResponseEntity.badRequest().body("Usuário fantasma não encontrado.");
        }

        Convite convite = new Convite();
        convite.setRemetente(remetente);
        convite.setDestinatario(destinatario);
        convite.setFilmeTmdbId(filmeTmdbId);
        convite.setStatus("PENDENTE");

        conviteRepository.save(convite);
        return ResponseEntity.ok(convite);
    }

    // 2. O sininho buscando se tem convite pendente
    @GetMapping("/pendentes/{usuarioId}")
    public ResponseEntity<List<Convite>> buscarPendentes(@PathVariable Long usuarioId) {
        List<Convite> pendentes = conviteRepository.findByDestinatarioIdAndStatusOrderByCreatedAtDesc(usuarioId, "PENDENTE");
        return ResponseEntity.ok(pendentes);
    }

    // 3. O clique de "Aceitar" ou "Recusar"
    @PutMapping("/{conviteId}/responder")
    public ResponseEntity<?> responderConvite(@PathVariable Long conviteId, @RequestBody Map<String, String> payload) {
        Convite convite = conviteRepository.findById(conviteId).orElse(null);
        if (convite == null) {
            return ResponseEntity.notFound().build();
        }

        String novoStatus = payload.get("status"); // Aqui vai chegar "ACEITO" ou "RECUSADO"
        convite.setStatus(novoStatus);
        conviteRepository.save(convite);

        return ResponseEntity.ok(convite);
    }

    // 4. A nova aba "Sessão Dupla" na Library buscando os pactos formados
    @GetMapping("/aceitos/{usuarioId}")
    public ResponseEntity<List<Convite>> buscarSessaoDupla(@PathVariable Long usuarioId) {
        // Traz os convites aceitos tanto se o usuário enviou quanto se ele recebeu
        List<Convite> aceitos = conviteRepository.findByDestinatarioIdAndStatusOrRemetenteIdAndStatus(usuarioId, "ACEITO", usuarioId, "ACEITO");
        return ResponseEntity.ok(aceitos);
    }
}