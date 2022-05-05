package br.com.batmelo.finance.importacao.domain.ativo.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.AtivoId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Ativo extends GenericEntityImpl<AtivoId> {

    @Id
    private AtivoId id;

    private String codigoAtivo;

    private String nome;

    private String descricao;

    private String setor;

    private String site;

    @Enumerated(EnumType.STRING)
    private TipoAtivo tipo;

    private BigDecimal preco;

    private String investingUrl;

    private String cnpj;

    private String nomeFontePagadora;

    private String cnpjFontePagadora;

    private LocalDateTime dataCotacao;

    private Long fetchCounter;

    public Ativo(AtivoBuilder builder) {
        this.id= builder.id;
        this.codigoAtivo = requireNonNull(builder.codigoAtivo);
        this.tipo = requireNonNull(builder.tipo);
        this.nome = builder.nome;
        this.descricao = builder.descricao;
        this.setor = builder.setor;
        this.site = builder.site;
        this.cnpj = builder.cnpj;
        this.nomeFontePagadora = builder.nomeFontePagadora;
    }

    public static AtivoBuilder builder() {
        return new AtivoBuilder();
    }

    public static void main(String[] args) {
        AtivoId.from("ce990335-0f41-45ee-b84b-0f3b1797d00e");
    }

}
