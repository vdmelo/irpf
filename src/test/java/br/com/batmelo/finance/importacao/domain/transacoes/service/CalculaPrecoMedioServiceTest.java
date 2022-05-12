package br.com.batmelo.finance.importacao.domain.transacoes.service;

import br.com.batmelo.finance.importacao.domain.transacoes.model.*;
import br.com.batmelo.finance.importacao.domain.transacoes.repository.OperacaoRepository;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.TransacaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculaPrecoMedioServiceTest {

    private OperacaoRepository operacaoRepository;

    private CalculaPrecoMedio calculaPrecoMedioService;

    private List<Operacao> listaDeOperacoes = new ArrayList<>();

    private Operacao operacaoCompra1;
    private Operacao operacaoCompra2;
    private Operacao operacaoCompra3;
    private Operacao operacaoCompra4;
    private Operacao operacaoCompra5;
    private Operacao operacaoVenda1;
    private Operacao operacaoVenda2;
    private Operacao operacaoVenda3;


    private AtivoId ativo = AtivoId.from("96a9bac9-5457-4ff4-b653-2d655d14123d");
    private InstituicaoId instituicao = InstituicaoId.from("96a9bac9-5457-4ff4-b653-2d655d14123d");

    @BeforeEach
    void beforeEach() {
        operacaoRepository = mock(OperacaoRepository.class);
        calculaPrecoMedioService = new CalculaPrecoMedio(operacaoRepository);
        setupOperacoes();
    }

    private OperacaoBuilder operacaoBaseBuilder() {
        return Operacao
                .builder()
                .instituicao(instituicao)
                .ativo(ativo)
                .tipoTrade(TipoTrade.SWING_TRADE)
                .transacao(TransacaoId.generate());

    }

    /*private OperacaoVendaBuilder operacaoBaseBuilder() {
        return OperacaoVenda
                .builder()
                .instituicao(instituicao)
                .ativo(ativo)
                .tipoTrade(TipoTrade.SWING_TRADE)
                .transacao(TransacaoId.generate());

    }*/

    private void setupOperacoes() {
        operacaoCompra1 = operacaoBaseBuilder()
                .data(LocalDate.parse("2020-03-12"))
                .tipoOperacao(TipoOperacaoFinanceira.COMPRA)
                .quantidade(BigDecimal.valueOf(35.0000000000))
                //.saldo(BigDecimal.valueOf(35.0000000000))
                .preco(BigDecimal.valueOf(49.0000000000))
                .build();

        operacaoCompra2 = operacaoBaseBuilder()
                .data(LocalDate.parse("2020-08-11"))
                .tipoOperacao(TipoOperacaoFinanceira.COMPRA)
                .quantidade(BigDecimal.valueOf(50.0000000000))
                //.saldo(BigDecimal.valueOf(50.0000000000))
                .preco(BigDecimal.valueOf(51.6200000000))
                .build();

        operacaoCompra3 = operacaoBaseBuilder()
                .data(LocalDate.parse("2020-11-13"))
                .tipoOperacao(TipoOperacaoFinanceira.COMPRA)
                .quantidade(BigDecimal.valueOf(15.0000000000))
                //.saldo(BigDecimal.valueOf(15.0000000000))
                .preco(BigDecimal.valueOf(45.0800000000))
                .build();

        operacaoCompra4 = operacaoBaseBuilder()
                .data(LocalDate.parse("2020-11-30"))
                .tipoOperacao(TipoOperacaoFinanceira.COMPRA)
                .quantidade(BigDecimal.valueOf(60.0000000000))
                ///.saldo(BigDecimal.valueOf(60.0000000000))
                .preco(BigDecimal.valueOf(43.6000000000))
                .build();

        operacaoCompra5 = operacaoBaseBuilder()
                .data(LocalDate.parse("2021-01-21"))
                .tipoOperacao(TipoOperacaoFinanceira.COMPRA)
                .quantidade(BigDecimal.valueOf(100.0000000000))
                //.saldo(BigDecimal.valueOf(100.0000000000))
                .preco(BigDecimal.valueOf(44.0600000000))
                .build();

        operacaoVenda1 = operacaoBaseBuilder()
                .data(LocalDate.parse("2021-05-25"))
                .tipoOperacao(TipoOperacaoFinanceira.VENDA)
                .quantidade(BigDecimal.valueOf(100.0000000000))
                //.saldo(BigDecimal.valueOf(100.0000000000))
                .preco(BigDecimal.valueOf(44.1600000000))
                .build();

        operacaoVenda2 = operacaoBaseBuilder()
                .data(LocalDate.parse("2021-12-17"))
                .tipoOperacao(TipoOperacaoFinanceira.VENDA)
                .quantidade(BigDecimal.valueOf(160.0000000000))
                //.saldo(BigDecimal.valueOf(160.0000000000))
                .preco(BigDecimal.valueOf(49.1600000000))
                .build();

        operacaoVenda3 = operacaoBaseBuilder()
                .data(LocalDate.parse("2021-12-17"))
                .tipoOperacao(TipoOperacaoFinanceira.VENDA)
                .quantidade(BigDecimal.valueOf(100.0000000000))
                //.saldo(BigDecimal.valueOf(100.0000000000))
                .preco(BigDecimal.valueOf(100))
                .build();

        listaDeOperacoes.add(operacaoCompra1);
        listaDeOperacoes.add(operacaoCompra2);
        listaDeOperacoes.add(operacaoCompra3);
        listaDeOperacoes.add(operacaoCompra4);
        listaDeOperacoes.add(operacaoCompra5);
        listaDeOperacoes.add(operacaoVenda1);
        listaDeOperacoes.add(operacaoVenda2);
        listaDeOperacoes.add(operacaoVenda3);
    }

    @Test
    void deveCalcularPrecoMedioCorretamente() {
        when(operacaoRepository.findByAtivoAndTipoTradeOrderByDataAsc(ativo, TipoTrade.SWING_TRADE))
                .thenReturn(listaDeOperacoes);

        calculaPrecoMedioService.calculaPrecoMedio(ativo, TipoTrade.SWING_TRADE);

        assertEquals(35, operacaoCompra1.obterSaldo().doubleValue());
        assertEquals(49, operacaoCompra1.obterPrecoMedioCompra().doubleValue());
        assertEquals(0, operacaoCompra1.obterPrecoMedioVenda().doubleValue());
        assertEquals(null, operacaoCompra1.getOperacaoAnterior());

        assertEquals(85, operacaoCompra2.obterSaldo().doubleValue());
        assertEquals(50.5411764706, operacaoCompra2.obterPrecoMedioCompra().doubleValue());
        assertEquals(0, operacaoCompra2.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoCompra1.getId(), operacaoCompra2.getOperacaoAnterior().getId());

        assertEquals( 100, operacaoCompra3.obterSaldo().doubleValue());
        assertEquals(49.722, operacaoCompra3.obterPrecoMedioCompra().doubleValue());
        assertEquals(0, operacaoCompra3.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoCompra2.getId(), operacaoCompra3.getOperacaoAnterior().getId());

        assertEquals(160, operacaoCompra4.obterSaldo().doubleValue());
        assertEquals(47.42625, operacaoCompra4.obterPrecoMedioCompra().doubleValue());
        assertEquals(0, operacaoCompra4.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoCompra3.getId(), operacaoCompra4.getOperacaoAnterior().getId());

        assertEquals(260, operacaoCompra5.obterSaldo().doubleValue());
        assertEquals(46.1315384615, operacaoCompra5.obterPrecoMedioCompra().doubleValue());
        assertEquals(0, operacaoCompra5.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoCompra4.getId(), operacaoCompra5.getOperacaoAnterior().getId());

        assertEquals(160, operacaoVenda1.obterSaldo().doubleValue());
        assertEquals(46.1315384615, operacaoVenda1.obterPrecoMedioCompra().doubleValue());
        assertEquals(44.16, operacaoVenda1.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoCompra5.getId(), operacaoVenda1.getOperacaoAnterior().getId());

        assertEquals(0, operacaoVenda2.obterSaldo().doubleValue());
        assertEquals(46.1315384615, operacaoVenda2.obterPrecoMedioCompra().doubleValue());
        assertEquals(47.2369230769, operacaoVenda2.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoVenda1.getId(), operacaoVenda2.getOperacaoAnterior().getId());

        assertEquals(-100, operacaoVenda3.obterSaldo().doubleValue());
        assertEquals(46.1315384615, operacaoVenda3.obterPrecoMedioCompra().doubleValue());
        assertEquals(61.8933333333, operacaoVenda3.obterPrecoMedioVenda().doubleValue());
        assertEquals(operacaoVenda1.getId(), operacaoVenda3.getOperacaoAnterior().getId());

    }

}

