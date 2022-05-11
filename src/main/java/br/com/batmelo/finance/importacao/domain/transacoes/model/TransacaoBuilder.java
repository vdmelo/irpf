package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.TransacaoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoBuilder {
    protected TransacaoId id;
    protected UsuarioId usuario;
    protected LocalDate data;
    protected TipoOperacaoFinanceira tipo;
    protected String mercado;
    protected String arquivo;
    protected Long prazo;
    protected InstituicaoId instituicao;
    protected AtivoId ativo;
    protected BigDecimal quantidade;
    protected BigDecimal preco;

    public TransacaoBuilder usuario(UsuarioId usuario) {
        this.usuario = usuario;
        return this;
    }

    public TransacaoBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public TransacaoBuilder tipo(TipoOperacaoFinanceira tipo) {
        this.tipo = tipo;
        return this;
    }

    public TransacaoBuilder mercado(String mercado) {
        this.mercado = mercado;
        return this;
    }

    public TransacaoBuilder arquivo(String arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public TransacaoBuilder prazo(Long prazo) {
        this.prazo = prazo;
        return this;
    }

    public TransacaoBuilder instituicao(InstituicaoId instituicao) {
        this.instituicao = instituicao;
        return this;
    }

    public TransacaoBuilder ativo(AtivoId ativo) {
        this.ativo = ativo;
        return this;
    }

    public TransacaoBuilder quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public TransacaoBuilder preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public Transacao build() {
        this.id = TransacaoId.generate();
        
        return new Transacao(this);
    }
}
