package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.sk.identifiers.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperacaoBuilder {

    protected OperacaoId id;
    protected UsuarioId usuario;
    protected InstituicaoId instituicao;
    protected AtivoId ativo;
    protected LocalDate data;
    protected TipoOperacaoFinanceira tipoOperacao;
    protected TipoTrade tipoTrade;
    protected BigDecimal quantidade;
    protected BigDecimal preco;
    protected BigDecimal saldo;
    protected TransacaoId transacao;

    public OperacaoBuilder transacao(Transacao transacao) {
        this.usuario = transacao.getUsuario();
        this.instituicao = transacao.getInstituicao();
        this.ativo = transacao.getAtivo();
        this.data = transacao.getData();
        this.tipoOperacao = transacao.getTipo();
        this.preco = transacao.getPreco();
        this.quantidade = transacao.getQuantidade();
        this.transacao = transacao.getId();
        this.tipoTrade = TipoTrade.SWING_TRADE;
        this.saldo = transacao.getQuantidade();

        return this;
    }

    public Operacao build() {
        this.id = OperacaoId.generate();

        return new Operacao(this);
    }
}
