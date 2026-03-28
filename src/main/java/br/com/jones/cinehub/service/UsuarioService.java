package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para cadastrar um novo usuário com regras de negócio
    public Usuario cadastrarUsuario(Usuario novoUsuario) {

        // Regra 1: Verifica se o email já existe
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(novoUsuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new RuntimeException("Já existe um usuário cadastrado com este email.");
        }

        // Regra 2: Verifica se o nickname já existe
        Optional<Usuario> nickExistente = usuarioRepository.findByNickname(novoUsuario.getNickname());
        if (nickExistente.isPresent()) {
            throw new RuntimeException("Este nickname já está em uso. Escolha outro.");
        }

        // Se passar pelas regras, salva no banco!
        return usuarioRepository.save(novoUsuario);
    }

    // Método para buscar um usuário pelo ID (vai ser útil para buscar o perfil depois)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));
    }
}