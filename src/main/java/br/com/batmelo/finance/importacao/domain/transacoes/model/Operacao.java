package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor

@DynamicUpdate
@Entity
public class Operacao extends GenericEntityImpl<OperacaoId> {
    @Id
    private OperacaoId id;

    @Column(name = "usuario_id")
    private UsuarioId usuario;

    @Column(name = "instituicao_id")
    private InstituicaoId instituicao;

    @Column(name = "ativo_id")
    private AtivoId ativo;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private TipoOperacaoFinanceira tipoOperacao;

    @Enumerated(EnumType.STRING)
    private TipoTrade tipoTrade;

    private BigDecimal quantidade;

    @Column(name = "saldo", precision = 8, scale = 2)
    private BigDecimal saldo;

    @Column(name = "preco", precision = 8, scale = 2)
    private BigDecimal preco;

    @Column(name = "emolumentos", precision = 8, scale = 2)
    private BigDecimal emolumentos;

    @Column(name = "taxa_liquidacao", precision = 8, scale = 2)
    private BigDecimal taxaLiquidacao;

    @Column(name = "lucro", precision = 8, scale = 2)
    private BigDecimal lucro;

    @Column(name = "preco_medio", precision = 12, scale = 6)
    private BigDecimal precoMedio;

    @Column(name = "transacao_id")
    private TransacaoId transacao;

    public Operacao(OperacaoBuilder builder) {
        this.tipoOperacao = builder.tipoOperacao;
        this.instituicao = builder.instituicao;
        this.usuario = builder.usuario;
        this.preco = builder.preco;
        this.quantidade = builder.quantidade;
        this.ativo = builder.ativo;
        this.transacao = builder.transacao;
        this.id = builder.id;
        this.data = builder.data;
        this.saldo = builder.saldo;
        this.tipoTrade = builder.tipoTrade;
    }

    public Operacao(Operacao operacao, OperacaoId id, TipoTrade tipoTrade, BigDecimal saldo) {
        this.tipoOperacao = operacao.tipoOperacao;
        this.instituicao = operacao.instituicao;
        this.usuario = operacao.usuario;
        this.preco = operacao.preco;
        this.ativo = operacao.ativo;
        this.transacao = operacao.transacao;
        this.data = operacao.data;

        this.id = id;
        this.tipoTrade = tipoTrade;
        this.quantidade = saldo;
        this.saldo = saldo;
    }

    public static OperacaoBuilder builder() {
        return new OperacaoBuilder();
    }

    public boolean temSaldo() {
        return this.getSaldo().doubleValue() > 0;
    }

    public void abateSaldoDayTrade(BigDecimal saldoAbater) {
        this.tipoTrade = TipoTrade.DAY_TRADE;
        this.saldo = this.saldo.subtract(saldoAbater);
    }

    public Operacao gerarOperacaoSwingTradeComSaldoRestante() {
        return new Operacao(this, OperacaoId.generate(), TipoTrade.SWING_TRADE, this.getSaldo());
    }

    public void zerarSaldo() {
        this.saldo = BigDecimal.ZERO;
    }

    public void converteEmDayTrade() {
        this.tipoTrade = TipoTrade.DAY_TRADE;
    }

    public void abateQuantidade(BigDecimal saldo) {
        this.quantidade = this.quantidade.subtract(saldo);
    }
}
