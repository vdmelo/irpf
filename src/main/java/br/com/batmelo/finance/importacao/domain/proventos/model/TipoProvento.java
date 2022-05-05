package br.com.batmelo.finance.importacao.domain.proventos.model;

public enum TipoProvento {

    JCP,
    DIVIDENDO,
    RENDIMENTO,
    RENDIMENTO_TRIBUTADO,
    AMORTIZACAO;


    public static TipoProvento convert( String str ) {
        switch (str) {
            case "REND. TRIBUTADO":
                return RENDIMENTO_TRIBUTADO;
            case "AMORTIZAÇÃO":
                return AMORTIZACAO;
            default:
                return valueOf(str);
        }
    }
}
