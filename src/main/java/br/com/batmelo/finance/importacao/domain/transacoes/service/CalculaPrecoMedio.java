package br.com.batmelo.finance.importacao.domain.transacoes.service;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.TipoTrade;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class CalculaPrecoMedio {

    private final OperacaoRepository operacaoRepository;

    public void calculaTodosOsPrecosMediosESaldosTodasOperacoes() {
        /*operacaoRepository.findAll()
                .stream()
                .map(Operacao::getAtivo)
                .distinct()
                .forEach(ativo -> {
                    this.calculaPrecoMedio(ativo, TipoTrade.FII);
                    this.calculaPrecoMedio(ativo, TipoTrade.DAY_TRADE);
                    this.calculaPrecoMedio(ativo, TipoTrade.SWING_TRADE);
                });*/

        this.calculaPrecoMedio(AtivoId.from("96a9bac9-5457-4ff4-b653-2d655d14123d"), TipoTrade.SWING_TRADE);

    }

    public void calculaPrecoMedio(AtivoId ativo, TipoTrade tipoTrade) {
        List<Operacao> operacoesDoAtivo = operacaoRepository.findByAtivoAndTipoTradeOrderByDataAsc(ativo, tipoTrade);

        if( !operacoesDoAtivo.isEmpty() ) {
            //List<Operacao> operacoesAnteriores = new ArrayList<>();
            Operacao operacaoAnterior = null;
            for (Operacao operacao: operacoesDoAtivo) {
                operacao.setOperacaoAnterior(operacaoAnterior);//calculaSaldoEPrecoMedio(operacoesAnteriores);
                operacaoAnterior = operacao;
                //operacoesAnteriores.add(operacao);
            }
        }
    }

}
