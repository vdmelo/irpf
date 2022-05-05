package br.com.batmelo.finance.importacao.domain.usuario.repository;

import br.com.batmelo.finance.importacao.domain.usuario.model.Usuario;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UsuarioId> {
}
