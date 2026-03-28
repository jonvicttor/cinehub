package br.com.jones.cinehub.repository;

import br.com.jones.cinehub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring Boot é tão inteligente que, só de escrever o nome do método assim,
    // ele já sabe que tem que fazer um "SELECT * FROM usuarios WHERE email = ?"
    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByNickname(String nickname);
}