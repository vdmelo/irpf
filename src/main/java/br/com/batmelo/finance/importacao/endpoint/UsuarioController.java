package br.com.batmelo.finance.importacao.endpoint;

import br.com.batmelo.finance.importacao.domain.usuario.app.UsuarioAppService;
import br.com.batmelo.finance.importacao.domain.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    private final UsuarioAppService service;

    @GetMapping
    public List<Usuario> teste() {
        return service.listarUsuarios();
    }

}
