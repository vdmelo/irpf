package br.com.batmelo.finance.importacao.domain.negociacao.model;

import br.com.batmelo.finance.importacao.domain.ativo.model.Ativo;
import br.com.batmelo.finance.importacao.domain.instituicao.model.Instituicao;
import br.com.batmelo.finance.importacao.domain.usuario.model.Usuario;
import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.InstituicaoId;
import br.com.batmelo.finance.sk.identifiers.NegociacaoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor

@Entity
public class Negociacao extends GenericEntityImpl<NegociacaoId> {

    @Id
    private NegociacaoId id;

    @Column(name = "usuario_id")
    private UsuarioId usuario;

    @Column(name = "data")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoOperacaoFinanceira tipo;

    @Column(name = "mercado")
    private String mercado;

    @Column(name = "arquivo")
    private String arquivo;

    @Column(name = "prazo")
    private Long prazo;

    @Column(name = "instituicao_id")
    private InstituicaoId instituicao;

    @Column(name = "ativo_id")
    private AtivoId ativo;

    @Column(name = "quantidade")
    private BigDecimal quantidade;

    @Column(name = "preco", precision = 8, scale = 2)
    private BigDecimal preco;

    public Negociacao(NegociacaoBuilder builder) {
        this.prazo = builder.prazo;
        this.preco = builder.preco;
        this.tipo = builder.tipo;
        this.mercado = builder.mercado;
        this.arquivo = builder.arquivo;
        this.instituicao = builder.instituicao;
        this.id = builder.id;
        this.data = builder.data;
        this.ativo = builder.ativo;
        this.quantidade = builder.quantidade;
        this.usuario = builder.usuario;
    }

    public static NegociacaoBuilder builder() {
        return new NegociacaoBuilder();
    }
}
