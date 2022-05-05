CREATE TABLE negociacao
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
    quantidade     NUMERIC(17,8),
    preco          NUMERIC(17,8),
    CONSTRAINT pk_negociacao PRIMARY KEY (id)
);

ALTER TABLE negociacao
    ADD CONSTRAINT FK_USUARIO_DA_NEGOCIACAO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE negociacao
    ADD CONSTRAINT FK_ATIVO_DA_NEGOCIACAO FOREIGN KEY (ativo_id) REFERENCES ativo (id);

ALTER TABLE negociacao
    ADD CONSTRAINT FK_INSTITUICAO_DA_NEGOCIACAO FOREIGN KEY (instituicao_id) REFERENCES instituicao (id);