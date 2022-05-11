package br.com.batmelo.finance.importacao.domain.transacoes.model;

public enum TipoTrade {

    SWING_TRADE,
    // operações iniciadas e encerradas, total ou parcialmente,
    // no mesmo dia, com a mesma instituição intermediadora e com o mesmo ativo
    DAY_TRADE,
    FII;
}
