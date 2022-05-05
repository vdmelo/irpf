package br.com.batmelo.finance.importacao.domain.instituicao.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@NoArgsConstructor

@Entity
@Table(name = "instituicao")
public class Instituicao extends GenericEntityImpl<InstituicaoId> {

    @Id
    private InstituicaoId id;

    private Integer codigoInstituicao;

    @Column(name = "nome", length = 128, nullable = false)
    private String nome;

    public static InstituicaoBuilder builder() {
        return new InstituicaoBuilder();
    }

    public Instituicao(InstituicaoBuilder builder) {
        this.id = builder.id;
        this.codigoInstituicao = builder.codigoInstituicao;
        this.nome = builder.nome;
    }
}
