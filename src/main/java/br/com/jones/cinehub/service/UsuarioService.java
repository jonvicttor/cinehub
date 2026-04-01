package br.com.jones.cinehub.service;

import br.com.jones.cinehub.model.Favorito;
import br.com.jones.cinehub.model.Usuario;
import br.com.jones.cinehub.repository.FavoritoRepository;
import br.com.jones.cinehub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FavoritoRepository favoritoRepository;

    public Usuario cadastrarUsuario(Usuario usuario) {
        Optional<Usuario> userEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (userEmail.isPresent() && !userEmail.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("Este e-mail já está cadastrado.");
        }

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

    public Usuario buscarPorNickname(String nickname) {
        return usuarioRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("Usuário @" + nickname + " não encontrado."));
    }

    public void seguirOuDeixarDeSeguir(Long usuarioId, Long seguidoId) {
        if (usuarioId.equals(seguidoId)) {
            throw new RuntimeException("Você não pode seguir a si mesmo!");
        }

        Usuario usuario = buscarPorId(usuarioId);
        Usuario seguido = buscarPorId(seguidoId);

        if (usuario.getSeguindo().contains(seguido)) {
            usuario.getSeguindo().remove(seguido);
        } else {
            usuario.getSeguindo().add(seguido);
        }

        usuarioRepository.save(usuario);
    }

    public Set<Usuario> listarQuemEuSigo(Long id) {
        return buscarPorId(id).getSeguindo();
    }

    // ==========================================
    // NOVO: LÓGICA DE FAVORITOS (TOP 6)
    // ==========================================
    public List<Favorito> listarFavoritos(Long usuarioId) {
        return favoritoRepository.findByUsuarioIdOrderByPosicaoAsc(usuarioId);
    }

    public void atualizarFavoritos(Long usuarioId, List<Long> filmesIds) {
        Usuario usuario = buscarPorId(usuarioId);

        // 1. Limpa os favoritos antigos do banco
        favoritoRepository.deleteByUsuarioId(usuarioId);

        // 2. Salva a nova ordem (limitando estritamente a 6 filmes)
        int limite = Math.min(filmesIds.size(), 6);
        for (int i = 0; i < limite; i++) {
            Favorito fav = new Favorito();
            fav.setUsuario(usuario);
            fav.setFilmeTmdbId(filmesIds.get(i));
            fav.setPosicao(i + 1);
            favoritoRepository.save(fav);
        }
    }
}