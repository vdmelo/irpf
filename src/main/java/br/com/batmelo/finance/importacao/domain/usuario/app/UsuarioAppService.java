package br.com.batmelo.finance.importacao.domain.usuario.app;

import br.com.batmelo.finance.importacao.domain.usuario.model.Usuario;
import br.com.batmelo.finance.importacao.domain.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@RequiredArgsConstructor

@Service
@Transactional
public class UsuarioAppService {

    private final UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }
}
