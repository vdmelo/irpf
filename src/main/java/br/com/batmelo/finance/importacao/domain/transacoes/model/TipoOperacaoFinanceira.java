package br.com.batmelo.finance.importacao.domain.transacoes.model;

import java.math.BigDecimal;

public enum TipoOperacaoFinanceira {
    COMPRA,
    VENDA;

    public TipoOperacaoFinanceira inverso() {
        if( this == COMPRA ) {
            return VENDA;
        }
        return COMPRA;
    }

    public boolean compra() {
        return this == COMPRA;
    }

    public boolean venda() {
        return this == VENDA;
    }

    public static class TipoOperacaoFinanceiraInvalidaException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561777L;

    }
}
