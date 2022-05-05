package br.com.batmelo.finance.importacao.domain.negociacao.model;

import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.NegociacaoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NegociacaoBuilder {
    protected NegociacaoId id;
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

    public NegociacaoBuilder usuario(UsuarioId usuario) {
        this.usuario = usuario;
        return this;
    }

    public NegociacaoBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public NegociacaoBuilder tipo(TipoOperacaoFinanceira tipo) {
        this.tipo = tipo;
        return this;
    }

    public NegociacaoBuilder mercado(String mercado) {
        this.mercado = mercado;
        return this;
    }

    public NegociacaoBuilder arquivo(String arquivo) {
        this.arquivo = arquivo;
        return this;
    }

    public NegociacaoBuilder prazo(Long prazo) {
        this.prazo = prazo;
        return this;
    }

    public NegociacaoBuilder instituicao(InstituicaoId instituicao) {
        this.instituicao = instituicao;
        return this;
    }

    public NegociacaoBuilder ativo(AtivoId ativo) {
        this.ativo = ativo;
        return this;
    }

    public NegociacaoBuilder quantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public NegociacaoBuilder preco(BigDecimal preco) {
        this.preco = preco;
        return this;
    }

    public Negociacao build() {
        this.id = NegociacaoId.generate();
        
        return new Negociacao(this);
    }
}
