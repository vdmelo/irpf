CREATE TABLE operacao
(
    id              UUID         NOT NULL,
    version        SMALLINT     NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT now(),
    usuario_id      UUID,
    instituicao_id UUID         NOT NULL,
    ativo_id       UUID         NOT NULL,
    data            date,
    tipo_operacao   VARCHAR(255),
    tipo_trade      VARCHAR(255),
    quantidade      DECIMAL(8, 2),
    saldo           DECIMAL(8, 2),
    preco           DECIMAL(8, 2),
    emolumentos     DECIMAL(8, 2),
    taxa_liquidacao DECIMAL(8, 2),
    lucro           DECIMAL(8, 2),
    preco_medio     DECIMAL(12, 6),
    transacao_id    UUID         NOT NULL,

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
