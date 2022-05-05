package br.com.batmelo.finance.importacao.domain.proventos.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.ProventoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)

@Entity
public class Provento extends GenericEntityImpl<ProventoId> {

    @Id
    private ProventoId id;

    private BigDecimal total;

    private BigDecimal liquido;

    @Enumerated(EnumType.STRING)
    private TipoProvento tipo;

    private LocalDate dataPagamento;

    private LocalDate dataCom;

    @Column(name = "ativo_id")
    private AtivoId ativo;

    @Column(name = "usuario_id")
    private UsuarioId usuario;

    public Provento(ProventoBuilder builder) {
        this.id = builder.id;
        this.ativo = builder.ativo;
        this.usuario = builder.usuario;
        this.liquido = builder.liquido;
        this.tipo = builder.tipo;
        this.total = builder.total;
        this.dataCom = builder.dataCom;
        this.dataPagamento = builder.dataPagamento;
    }

    public static ProventoBuilder builder() {
        return new ProventoBuilder();
    }

}
