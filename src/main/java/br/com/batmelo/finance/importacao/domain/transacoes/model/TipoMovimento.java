package br.com.batmelo.finance.importacao.domain.transacoes.model;

import java.math.BigDecimal;

public enum TipoMovimento {
    COMPRA,
    VENDA;

    public TipoMovimento inverso() {
        if( this == COMPRA ) {
            return VENDA;
        }
        return COMPRA;
    }


    public static class TipoOperacaoFinanceiraInvalidaException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561777L;

    }
}
