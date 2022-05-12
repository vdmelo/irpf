CREATE TABLE transacao
(
    id             UUID         NOT NULL,
    version        SMALLINT     NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT now(),
    usuario_id     UUID,
    data           date,
    tipo           VARCHAR(255) NOT NULL,
    mercado        VARCHAR(255),
    arquivo        VARCHAR(255),
    prazo          BIGINT,
    instituicao_id UUID         NOT NULL,
    ativo_id       UUID         NOT NULL,
    quantidade     numeric(20, 10),
    preco          numeric(20, 10),
    CONSTRAINT pk_transacao PRIMARY KEY (id)
);

ALTER TABLE transacao
    ADD CONSTRAINT FK_USUARIO_DA_TRANSACAO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE transacao
    ADD CONSTRAINT FK_ATIVO_DA_TRANSACAO FOREIGN KEY (ativo_id) REFERENCES ativo (id);

ALTER TABLE transacao
    ADD CONSTRAINT FK_INSTITUICAO_DA_TRANSACAO FOREIGN KEY (instituicao_id) REFERENCES instituicao (id);