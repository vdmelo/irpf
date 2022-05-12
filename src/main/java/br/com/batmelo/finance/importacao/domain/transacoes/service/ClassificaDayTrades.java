package br.com.batmelo.finance.importacao.domain.transacoes.service;

import br.com.batmelo.finance.importacao.domain.transacoes.model.Operacao;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor

@Service
public class ClassificaDayTrades {

    private final OperacaoRepository operacaoRepository;

    public void classificaDayTradesTodasOperacoes() {
        operacaoRepository.findAll()
                .stream()
                .forEach(this::classificaDayTrade);

    }

    private void classificaDayTrade(Operacao operacao) {
        List<? extends Operacao> operacoesInversasNaMesmaData = obterOperacoesInversasNaMesmaData(operacao);

        if (!operacoesInversasNaMesmaData.isEmpty()) {

            operacao.converteEmDayTrade();

            this.converteSaldoNecessarioEmDayTrade(operacao, operacoesInversasNaMesmaData);

            this.criaOperacaoSwingTradeComSaldoRestante(operacao);
        }

        //operacaoRepository.save(operacao);
    }

    private List<? extends Operacao> obterOperacoesInversasNaMesmaData(Operacao operacao) {
        return operacaoRepository
                .findOperacoesDeVendaDoUsuarioNaInstituicaoNaDataComSaldoPositivo(
                        operacao.getAtivo(),
                        operacao.getInstituicao(),
                        operacao.getData(),
                        operacao.getTipoOperacao().inverso());
    }

    /*private Class<? extends Operacao> tipoInverso(Operacao operacao) {
        if (operacao instanceof OperacaoCompra)
            return OperacaoVenda.class;
        return OperacaoCompra.class;
    }*/

    private void converteSaldoNecessarioEmDayTrade(Operacao operacao, List<? extends Operacao> operacoesInversas) {
        for (Operacao operacaoInversa : operacoesInversas) {
            if (operacao.isSaldoPositivo()) {
                //this.abateSaldoOperacaoDayTrade(operacao, operacaoInversa);
                operacao.transformaEmDayTrade();
                operacaoInversa.transformaEmDayTrade();

                this.criaOperacaoSwingTradeComSaldoRestante(operacaoInversa);
            }
        }
    }

    private void criaOperacaoSwingTradeComSaldoRestante(Operacao operacao) {
        if (operacao.isSaldoPositivo()) {
            Operacao operacaoSwingTrade = operacao.gerarOperacaoSwingTradeComSaldoRestante();
            operacao.abateQuantidade(operacao.obterSaldo());

            operacaoRepository.save(operacaoSwingTrade);
            //operacaoRepository.save(operacao);
        }
    }

    /*private void abateSaldoOperacaoDayTrade(Operacao operacao, Operacao operacaoInversa) {
        BigDecimal saldoOperacao = operacao.getSaldo();
        BigDecimal saldoOperacaoInversa = operacaoInversa.getSaldo();

        if (saldoOperacao.doubleValue() >= saldoOperacaoInversa.doubleValue()) {
            operacao.abateSaldoDayTrade(saldoOperacaoInversa);
            operacaoInversa.abateSaldoDayTrade(saldoOperacaoInversa);
        } else {
            operacao.abateSaldoDayTrade(saldoOperacao);
            operacaoInversa.abateSaldoDayTrade(saldoOperacao);
        }

        //operacaoRepository.save(operacao);
        //operacaoRepository.save(operacaoInversa);
    }*/
}
