/*package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor

@Entity
@DiscriminatorValue(TipoOperacao.COMPRA)
public class OperacaoCompra extends Operacao<OperacaoCompraBuilder, OperacaoCompra> {

    @Column(name = "preco_medio_compra", precision = 12, scale = 6)
    private BigDecimal precoMedioCompra;

    @Column(name = "quantidade_acumulada_compra", precision = 20, scale = 10)
    private BigDecimal quantidadeAcumuladaCompra;

    @Column(name = "operacao_anterior_compra_id")
    private OperacaoId operacaoAnteriorCompra;

    public OperacaoCompra(OperacaoCompraBuilder builder) {
        super(builder);
        this.precoMedioCompra = BigDecimal.ZERO;
    }

    public static OperacaoCompraBuilder builder() {
        return new OperacaoCompraBuilder();
    }

    public OperacaoCompra gerarOperacaoSwingTradeComSaldoRestante() {
        return gerarOperacaoSwingTradeComSaldoRestante(OperacaoCompra.builder())
                .build();
    }

    public void inicializaPrecoMedio() {
        this.precoMedioCompra = this.getPreco();
    }

    public void calculaSaldoEPrecoMedio(Operacao operacaoAnterior, OperacaoCompra operacaoCompraAnterior) {
        super.setOperacaoAnterior(operacaoAnterior.getId());
        this.operacaoAnteriorCompra = operacaoCompraAnterior.getId();
        setSaldo(operacaoAnterior.getSaldo().add(this.getQuantidade()));
        if( temSaldo() ) {
            this.precoMedioCompra = calculaPrecoMedio(operacaoCompraAnterior.getPrecoMedioCompra(), operacaoAnterior.getSaldo());
        }
    }

}
*/