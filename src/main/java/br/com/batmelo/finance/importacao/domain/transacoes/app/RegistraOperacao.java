package br.com.batmelo.finance.importacao.domain.transacoes.app;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.model.Transacao;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor

@Service
@Transactional
public class RegistraOperacao {

    private final OperacaoRepository operacaoRepository;

    private final TransacaoRepository transacaoRepository;

    public void registraOperacaoParaTodasAsTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAllByOrderByDataAsc();
        transacoes.stream().forEach(this::registrarOperacao);
    }

    private void registrarOperacao(Transacao transacao) {
        Operacao operacao = Operacao.builder()
                .transacao(transacao)
                .build();

        List<Operacao> operacoesInversas = operacaoRepository
                .findOperacoesDoUsuarioNaInstituicaoNaDataComSaldoPositivo(
                        operacao.getAtivo(),
                        operacao.getInstituicao(),
                        operacao.getData(),
                        operacao.getTipoOperacao().inverso());

        if (!operacoesInversas.isEmpty()) {

            operacao.converteEmDayTrade();

            converteSaldoNecessarioEmDayTrade(operacao, operacoesInversas);

            if (operacao.temSaldo()) {
                this.criaOperacaoSwingTradeComSaldoRestante(operacao);
            }
        }
        operacaoRepository.save(operacao);
    }

    private void converteSaldoNecessarioEmDayTrade(Operacao operacao, List<Operacao> operacoesInversas) {
        for (Operacao operacaoInversa : operacoesInversas) {
            if (operacao.temSaldo()) {
                abateSaldoOperacaoDayTrade(operacao, operacaoInversa);
                if( operacaoInversa.temSaldo() ) {
                    this.criaOperacaoSwingTradeComSaldoRestante(operacaoInversa);
                }
            }
        }
    }

    private void criaOperacaoSwingTradeComSaldoRestante(Operacao operacao) {
        Operacao operacaoSwingTrade = operacao.gerarOperacaoSwingTradeComSaldoRestante();
        operacao.abateQuantidade(operacao.getSaldo());
        operacao.zerarSaldo();
        operacaoRepository.save(operacaoSwingTrade);
        operacaoRepository.save(operacao);
    }

    private void abateSaldoOperacaoDayTrade(Operacao operacao, Operacao operacaoInversa) {
        BigDecimal saldoOperacao = operacao.getSaldo();
        BigDecimal saldoOperacaoInversa = operacaoInversa.getSaldo();

        if( saldoOperacao.doubleValue()>=saldoOperacaoInversa.doubleValue() ) {
            operacao.abateSaldoDayTrade(saldoOperacaoInversa);
            operacaoInversa.abateSaldoDayTrade(saldoOperacaoInversa);
        } else {
            operacao.abateSaldoDayTrade(saldoOperacao);
            operacaoInversa.abateSaldoDayTrade(saldoOperacao);
        }

        operacaoRepository.save(operacao);
        operacaoRepository.save(operacaoInversa);
    }

}
