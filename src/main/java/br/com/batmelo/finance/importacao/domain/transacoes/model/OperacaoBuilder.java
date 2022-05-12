package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.sk.identifiers.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperacaoBuilder/*<B extends OperacaoBuilder, M extends Operacao>*/ {
    protected OperacaoId id;
    protected UsuarioId usuario;
    protected InstituicaoId instituicao;
    protected AtivoId ativo;
    protected LocalDate data;
    protected TipoOperacaoFinanceira tipoOperacao;
    protected TipoTrade tipoTrade;
    protected BigDecimal quantidade;
    protected BigDecimal saldo;
    protected BigDecimal preco;

    protected TransacaoId transacao;

    public OperacaoBuilder usuario(UsuarioId usuario) {
        this.usuario = usuario;
        return this;
    }

    public OperacaoBuilder instituicao(InstituicaoId instituicao) {
        this.instituicao = instituicao;
        return this;
    }

    public OperacaoBuilder ativo(AtivoId ativo) {
        this.ativo = ativo;
        return this;
    }

    public OperacaoBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public OperacaoBuilder tipoOperacao(TipoOperacaoFinanceira tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        return this;
    }

    public OperacaoBuilder tipoTrade(TipoTrade tipoTrade) {
        this.tipoTrade = tipoTrade;
        return this;
    }

    public OperacaoBuilder quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public OperacaoBuilder saldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public OperacaoBuilder preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public OperacaoBuilder transacao(TransacaoId transacao) {
        this.transacao = transacao;
        return this;
    }

    public Operacao build() {
        this.id = OperacaoId.generate();

        return new Operacao(this);
    }

}
