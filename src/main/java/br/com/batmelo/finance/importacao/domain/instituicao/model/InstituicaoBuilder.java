package br.com.batmelo.finance.importacao.domain.instituicao.model;

import br.com.batmelo.finance.sk.identifiers.InstituicaoId;

public class InstituicaoBuilder {

    protected InstituicaoId id;
    protected Integer codigoInstituicao;
    protected String nome;

    public InstituicaoBuilder codigoInstituicao(Integer codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
        return this;
    }

    public InstituicaoBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public Instituicao build() {
        this.id = InstituicaoId.generate();
        
        return new Instituicao(this);
    }
}
