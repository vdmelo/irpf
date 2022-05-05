package br.com.batmelo.finance.importacao.domain.proventos.model;

import br.com.batmelo.finance.sk.identifiers.AtivoId;
import br.com.batmelo.finance.sk.identifiers.ProventoId;
import br.com.batmelo.finance.sk.identifiers.UsuarioId;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ProventoBuilder {

    protected ProventoId id;
    protected BigDecimal total;
    protected BigDecimal liquido;
    protected TipoProvento tipo;
    protected LocalDate dataPagamento;
    protected LocalDate dataCom;
    protected AtivoId ativo;
    protected UsuarioId usuario;

    public ProventoBuilder total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public ProventoBuilder liquido(BigDecimal liquido) {
        this.liquido = liquido;
        return this;
    }

    public ProventoBuilder tipo(TipoProvento tipo) {
        this.tipo = tipo;
        return this;
    }

    public ProventoBuilder dataPagamento(LocalDate dataPagamento) {
        this.dataPagamento = dataPagamento;
        return this;
    }

    public ProventoBuilder dataCom(LocalDate dataCom) {
        this.dataCom = dataCom;
        return this;
    }

    public ProventoBuilder ativo(AtivoId ativo) {
        this.ativo = ativo;
        return this;
    }

    public ProventoBuilder usuario(UsuarioId usuario) {
        this.usuario = usuario;
        return this;
    }

    public Provento build() {
        this.id = ProventoId.generate();
        return new Provento(this);
    }
}
