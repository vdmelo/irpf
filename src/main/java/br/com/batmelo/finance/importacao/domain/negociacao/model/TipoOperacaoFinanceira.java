package br.com.batmelo.finance.importacao.domain.negociacao.model;

import br.com.batmelo.finance.sk.identifiers.NegociacaoId;

import java.util.function.Function;

public enum TipoOperacaoFinanceira {
    COMPRA,
    VENDA;

    public static class TipoOperacaoFinanceiraInvalidaException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561777L;

    }
}
