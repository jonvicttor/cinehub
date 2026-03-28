package br.com.jones.cinehub.controller;

import br.com.jones.cinehub.model.Amizade;
import br.com.jones.cinehub.model.StatusAmizade;
import br.com.jones.cinehub.service.AmizadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/amizades")
public class AmizadeController {

    @Autowired
    private AmizadeService amizadeService;

    // Rota para enviar pedido: /amizades/enviar?de=1&para=2
    @PostMapping("/enviar")
    public ResponseEntity<Amizade> enviarPedido(@RequestParam Long de, @RequestParam Long para) {
        return ResponseEntity.ok(amizadeService.enviarPedido(de, para));
    }

    // Rota para aceitar: /amizades/1/responder?status=ACEITA
    @PutMapping("/{id}/responder")
    public ResponseEntity<Amizade> responder(@PathVariable Long id, @RequestParam StatusAmizade status) {
        return ResponseEntity.ok(amizadeService.responderPedido(id, status));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Amizade>> listar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(amizadeService.listarAmizades(usuarioId));
    }
}