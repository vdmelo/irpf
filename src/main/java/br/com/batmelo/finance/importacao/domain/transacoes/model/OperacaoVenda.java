/*package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.sk.identifiers.OperacaoId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor

@Entity
@DiscriminatorValue(TipoOperacao.VENDA)
public class OperacaoVenda extends Operacao<OperacaoVendaBuilder, OperacaoVenda> {

    @Column(name = "preco_medio_venda", precision = 12, scale = 6)
    private BigDecimal precoMedioVenda;

    @Column(name = "quantidade_acumulada_venda", precision = 20, scale = 10)
    private BigDecimal quantidadeAcumuladaVenda;

    @Column(name = "operacao_anterior_venda_id")
    private OperacaoId operacaoAnteriorVenda;

    public OperacaoVenda(OperacaoVendaBuilder builder) {
        super(builder);
        this.precoMedioVenda = BigDecimal.ZERO;
    }

    public static OperacaoVendaBuilder builder() {
        return new OperacaoVendaBuilder();
    }

    public OperacaoVenda gerarOperacaoSwingTradeComSaldoRestante() {
        return gerarOperacaoSwingTradeComSaldoRestante(OperacaoVenda.builder())
                .build();
    }

    public void inicializaPrecoMedio() {
        this.precoMedioVenda = this.getPreco();
    }

    public void calculaSaldoEPrecoMedio(OperacaoVenda operacaoAnterior) {
        setOperacaoAnterior(operacaoAnterior.getId());
        setSaldo(operacaoAnterior.getSaldo().subtract(this.getQuantidade()));

        if( temSaldo() ) {
            this.precoMedioVenda = calculaPrecoMedio(operacaoAnterior.getPrecoMedioVenda(), operacaoAnterior.getSaldo());
        }
    }

}
*/