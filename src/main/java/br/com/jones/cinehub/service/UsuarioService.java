package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verifica se o e-mail existe e pertence a OUTRA pessoa
        Optional<Usuario> userEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (userEmail.isPresent() && !userEmail.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

        // Verifica se o nickname existe e pertence a OUTRA pessoa
        Optional<Usuario> userNick = usuarioRepository.findByNickname(usuario.getNickname());
        if (userNick.isPresent() && !userNick.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Este nickname já está em uso.");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario fazerLogin(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(user -> user.getSenha().equals(senha))
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos."));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }

    // NOVO: Busca por Nickname para o Social
    public Usuario buscarPorNickname(String nickname) {
        return usuarioRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário @" + nickname + " não encontrado."));
    }

    // NOVO: Lógica de Seguir/Parar de Seguir
    public void seguirOuDeixarDeSeguir(Long usuarioId, Long seguidoId) {
        if (usuarioId.equals(seguidoId)) {
            throw new RuntimeException("Você não pode seguir a si mesmo!");
        }

        Usuario usuario = buscarPorId(usuarioId);
        Usuario seguido = buscarPorId(seguidoId);

        if (usuario.getSeguindo().contains(seguido)) {
            usuario.getSeguindo().remove(seguido); // Unfollow
        } else {
            usuario.getSeguindo().add(seguido); // Follow
        }

        usuarioRepository.save(usuario);
    }

    public Set<Usuario> listarQuemEuSigo(Long id) {
        return buscarPorId(id).getSeguindo();
    }
}