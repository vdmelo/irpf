package br.com.batmelo.finance.importacao.domain.transacoes.model;

public enum TipoOperacaoFinanceira {
    COMPRA,
    VENDA;

    public TipoOperacaoFinanceira inverso() {
        if( this == COMPRA ) {
            return VENDA;
        }
        return COMPRA;
    }

    public static class TipoOperacaoFinanceiraInvalidaException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561777L;

    }
}
