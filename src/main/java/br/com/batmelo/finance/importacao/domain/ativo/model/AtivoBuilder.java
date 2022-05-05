package br.com.batmelo.finance.importacao.domain.ativo.model;

import br.com.batmelo.finance.sk.identifiers.AtivoId;

public final class AtivoBuilder {
    protected AtivoId id;
    protected String codigoAtivo;
    protected String nome;
    protected String descricao;
    protected TipoAtivo tipo;
    protected String site;
    protected String setor;
    protected String cnpj;
    protected String nomeFontePagadora;


    public AtivoBuilder codigoAtivo(String codigoAtivo) {
        this.codigoAtivo = codigoAtivo;
        return this;
    }

    public AtivoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public AtivoBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public AtivoBuilder tipo(TipoAtivo tipo) {
        this.tipo = tipo;
        return this;
    }

    public AtivoBuilder site(String site) {
        this.site = site;
        return this;
    }
    public AtivoBuilder setor(String setor) {
        this.setor = setor;
        return this;
    }

    public AtivoBuilder cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }
    public AtivoBuilder nomeFontePagadora(String nomeFontePagadora) {
        this.nomeFontePagadora = nomeFontePagadora;
        return this;
    }
    public Ativo build() {
        this.id = AtivoId.generate();
        return new Ativo(this);
    }
}
