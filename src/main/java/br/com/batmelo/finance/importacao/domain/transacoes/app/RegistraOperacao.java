package br.com.batmelo.finance.importacao.domain.transacoes.app;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.OperacaoBuilder;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoTrade;
import br.com.batmelo.finance.importacao.domain.transacoes.model.Transacao;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.TransacaoRepository;
import br.com.batmelo.finance.importacao.domain.transacoes.service.CalculaPrecoMedio;
import br.com.batmelo.finance.importacao.domain.transacoes.service.ClassificaDayTrades;
import br.com.batmelo.finance.importacao.domain.transacoes.service.ClassificaFII;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistraOperacao {

    private final OperacaoRepository operacaoRepository;

    private final TransacaoRepository transacaoRepository;

    private final ClassificaDayTrades classificaDayTrades;

    private final ClassificaFII classificaFII;

    private final CalculaPrecoMedio calculaPrecoMedio;

    public void registraOperacaoParaTodasAsTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAllByOrderByDataAsc();

        transacoes.stream()
                .map(Transacao::gerarOperacao)
                .forEach(operacao -> {
                    operacaoRepository.save(operacao);
                });

        classificaFII.classificaFIITodasOperacoes();;
        classificaDayTrades.classificaDayTradesTodasOperacoes();
        //calculaPrecoMedio.calculaTodosOsPrecosMediosESaldosTodasOperacoes();
    }

}
