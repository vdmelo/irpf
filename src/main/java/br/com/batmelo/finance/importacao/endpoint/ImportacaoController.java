package br.com.batmelo.finance.importacao.endpoint;

import br.com.batmelo.finance.importacao.domain.ativo.app.ImportaEmpresaAppService;
import br.com.batmelo.finance.importacao.domain.proventos.app.CarregaProventosStatusInvestJson;
import br.com.batmelo.finance.importacao.domain.transacoes.app.ImportaTransacoesStatusInvest;
import br.com.batmelo.finance.importacao.domain.transacoes.app.RegistraOperacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

@RequiredArgsConstructor

@RestController
@RequestMapping(path = "/api/v1/importacao", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImportacaoController {

    private final CarregaProventosStatusInvestJson carregaProventosStatusInvestJson;

    private final ImportaEmpresaAppService importaEmpresaAppService;

    private final ImportaTransacoesStatusInvest importaTransacoesStatusInvest;


    private final RegistraOperacao registraOperacao;

    @GetMapping
    public String teste() {
        return "teste";
    }

    @PostMapping(path = "/carrega-empresas")
    public void carregaEmpresas() throws IOException {
        importaEmpresaAppService.importaListaEmpresas();
    }

    @PostMapping(path = "/carrega-proventos")
    public void carregaProventos() {
        carregaProventosStatusInvestJson.carregaProventosStatusInvest(new File("C:\\Users\\vdmel\\IdeaProjects\\irpf\\src\\main\\resources\\db\\statusinvest\\proventos.json"));
    }

    @PostMapping(path = "/carrega-transacoes")
    public void carregaTransacoes() throws ParseException, IOException {
        importaTransacoesStatusInvest.importar(new File("C:\\Users\\vdmel\\IdeaProjects\\irpf\\src\\main\\resources\\db\\statusinvest\\carteira-export.xlsx"));
    }

    @PostMapping(path = "/carrega-operacoes")
    public void carregaOperacoes() {
        registraOperacao.registraOperacaoParaTodasAsTransacoes();
    }
}
