package br.com.batmelo.finance.importacao.endpoint;

import br.com.batmelo.finance.importacao.domain.ativo.app.ImportaFIIAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RestController
@RequestMapping(path="/api/v1/ativos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AtivoController {

    private final ImportaFIIAppService importaFii;

    @PostMapping(path="/cria-fiis")
    public void criaFiis() {
        importaFii.importaFiis();
    }
}
