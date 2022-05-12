CREATE TABLE operacao
(
    id                          UUID      NOT NULL,
    version                     SMALLINT  NOT NULL DEFAULT 0,
    created_at                  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at                  TIMESTAMP NOT NULL DEFAULT now(),
    usuario_id                  UUID,
    instituicao_id              UUID      NOT NULL,
    ativo_id                    UUID      NOT NULL,
    data                        date,
    tipo_operacao               VARCHAR(255),
    tipo_trade                  VARCHAR(255),
    quantidade                  numeric(20, 10),
    saldo                       numeric(20, 10),
    preco                       numeric(20, 10),
    emolumentos                 numeric(20, 10),
    taxa_liquidacao             numeric(20, 10),
    lucro                       numeric(20, 10),
    transacao_id                UUID      NOT NULL,
    operacao_anterior_id        UUID,
    preco_medio_compra          numeric(20, 10),
    quantidade_acumulada_compra numeric(20, 10),
    operacao_anterior_compra_id UUID,
    preco_medio_venda           numeric(20, 10),
    quantidade_acumulada_venda  numeric(20, 10),
    operacao_anterior_venda_id  UUID,

    CONSTRAINT pk_operacao PRIMARY KEY (id)
);

ALTER TABLE operacao
    ADD CONSTRAINT FK_USUARIO_DA_OPERACAO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_ATIVO_DA_OPERACAO FOREIGN KEY (ativo_id) REFERENCES ativo (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_INSTITUICAO_DA_OPERACAO FOREIGN KEY (instituicao_id) REFERENCES instituicao (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_TRANSACAO_DA_OPERACAO FOREIGN KEY (transacao_id) REFERENCES transacao (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_OPERACAO_ANTERIOR_DA_OPERACAO FOREIGN KEY (operacao_anterior_id) REFERENCES operacao (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_OPERACAO_ANTERIOR_COMPRA_DA_OPERACAO FOREIGN KEY (operacao_anterior_compra_id) REFERENCES operacao (id);

ALTER TABLE operacao
    ADD CONSTRAINT FK_OPERACAO_ANTERIOR_VENDA_DA_OPERACAO FOREIGN KEY (operacao_anterior_venda_id) REFERENCES operacao (id);
