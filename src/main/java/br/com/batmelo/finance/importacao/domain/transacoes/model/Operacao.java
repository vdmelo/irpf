package br.com.batmelo.finance.importacao.domain.transacoes.model;

import br.com.batmelo.finance.infra.model.GenericEntityImpl;
import br.com.batmelo.finance.sk.identifiers.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED, force = true)

@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = TipoOperacao.COLUMN, discriminatorType = DiscriminatorType.STRING)
public class Operacao/*<B extends OperacaoBuilder<B,M>, M extends Operacao>*/ extends GenericEntityImpl<OperacaoId> {

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

    @Column(name = "quantidade", precision = 20, scale = 10)
    private BigDecimal quantidade;

    //@Column(name = "saldo", precision = 20, scale = 10)
    //@Setter(PROTECTED)
    //private BigDecimal saldo;

    @Column(name = "preco", precision = 20, scale = 10)
    private BigDecimal preco;

    @Column(name = "emolumentos", precision = 20, scale = 10)
    private BigDecimal emolumentos;

    @Column(name = "taxa_liquidacao", precision = 20, scale = 10)
    private BigDecimal taxaLiquidacao;

    @Column(name = "lucro", precision = 20, scale = 10)
    private BigDecimal lucro;

    @Column(name = "transacao_id")
    private TransacaoId transacao;

    //@Column(name = "operacao_anterior_id")
    //@Setter(PROTECTED)
    //private OperacaoId operacaoAnterior;

    @OneToOne
    @Column(name = "operacao_anterior_id")
    @Setter
    private Operacao operacaoAnterior;

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
        //this.saldo = builder.saldo;
        this.tipoTrade = builder.tipoTrade;
    }

    public static OperacaoBuilder builder() {
        return new OperacaoBuilder();
    }

    public Operacao gerarOperacaoSwingTradeComSaldoRestante() {
        BigDecimal saldo = this.obterSaldo();
        if( saldo.doubleValue()<0) {
            saldo = saldo.negate();
        }

        return Operacao.builder()
                .usuario(this.usuario)
                .instituicao(this.instituicao)
                .ativo(this.ativo)
                .data(this.data)
                .preco(this.preco)
                .quantidade(saldo)
                .transacao(this.transacao)
                .tipoTrade(TipoTrade.SWING_TRADE)
                //.saldo(this.getSaldo())
                .build();
    }

    public boolean isSaldoPositivo() {
        return this.obterSaldo().doubleValue() > 0;
    }

/*    public void zerarSaldo() {
        this.saldo = BigDecimal.ZERO;
    }*/

    public void converteEmDayTrade() {
        this.tipoTrade = TipoTrade.DAY_TRADE;
    }

    public void abateQuantidade(BigDecimal saldo) {
        this.quantidade = this.quantidade.subtract(saldo);
    }

/*    public void abateSaldoDayTrade(BigDecimal saldoAbater) {
        transformaEmDayTrade();
        this.saldo = this.saldo.subtract(saldoAbater);
    }*/

    public void transformaEmFII() {
        this.tipoTrade = TipoTrade.FII;
    }

    public void transformaEmDayTrade() {
        this.tipoTrade = TipoTrade.DAY_TRADE;
    }

    public BigDecimal valorOperacaoSemTaxas() {
        return this.getQuantidade().multiply(this.getPreco());
    }

/*    protected BigDecimal calculaPrecoMedio(BigDecimal precoMedioAnterior, BigDecimal saldoAnterior) {
        precoMedioAnterior = requireNonNullElse(precoMedioAnterior, BigDecimal.ZERO);
        saldoAnterior = requireNonNullElse(saldoAnterior, BigDecimal.ZERO);
        return calculaValorTotalDasOperacoes(precoMedioAnterior, saldoAnterior)
                .divide(this.getSaldo(), 10, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculaValorTotalDasOperacoes(BigDecimal precoMedioAnterior, BigDecimal saldoAnterior) {
        return precoMedioAnterior.multiply(saldoAnterior)
                .add(this.valorOperacaoSemTaxas());
    }*/

    private BigDecimal obterPrecoMedio(Predicate<Operacao> filterTipoOperacao) {
        List<Operacao> operacoes = sequenciaDeOperacoes();
        if (!operacoes.isEmpty()) {
            BigDecimal valorTotalCompras = operacoes.stream()
                    .filter(filterTipoOperacao)
                    .map(Operacao::valorOperacaoSemTaxas)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal quantidadeTotalCompras = operacoes.stream()
                    .filter(filterTipoOperacao)
                    .map(Operacao::getQuantidade)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if( quantidadeTotalCompras.doubleValue() == 0) {
                return BigDecimal.ZERO;
            }

            return valorTotalCompras.divide(quantidadeTotalCompras, 10, RoundingMode.HALF_EVEN);
        }
        return getPreco();
    }

    public BigDecimal obterPrecoMedioCompra() {
        return obterPrecoMedio(Operacao::isCompra);
    }

    public BigDecimal obterPrecoMedioVenda() {
        return obterPrecoMedio(Operacao::isVenda);
    }

    public BigDecimal obterSaldo() {
        List<Operacao> operacoes = sequenciaDeOperacoes();

        return operacoes.stream()
                .map(operacao -> {
                    if (operacao.isCompra()) {
                        return operacao.getQuantidade();
                    }
                    return operacao.getQuantidade().negate();
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isCompra() {
        return this.tipoOperacao.compra();// == TipoOperacaoFinanceira.COMPRA;
    }

    private boolean isVenda() {
        return this.tipoOperacao.venda();// == TipoOperacaoFinanceira.VENDA;
    }

    private List<Operacao> sequenciaDeOperacoes() {
        Operacao operacao = this;
        List<Operacao> sequenciaDeOperacoes = new ArrayList<>();
        sequenciaDeOperacoes.add(this);
        while (operacao.getOperacaoAnterior() != null) {
            sequenciaDeOperacoes.add(operacao.getOperacaoAnterior());
            operacao = operacao.getOperacaoAnterior();
        }
        Collections.reverse(sequenciaDeOperacoes);
        return sequenciaDeOperacoes;
    }


    //public abstract void inicializaPrecoMedio();

    //public abstract void calculaSaldoEPrecoMedio(List<Operacao> operacoesAnteriores);
}
