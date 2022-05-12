CREATE TABLE provento
(
    id             UUID         NOT NULL,
    version        SMALLINT     NOT NULL DEFAULT 0,
    created_at     TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at     TIMESTAMP    NOT NULL DEFAULT now(),
    total          numeric(20, 10),
    liquido        numeric(20, 10),
    total_liquido  numeric(20, 10),
    tipo           VARCHAR(255) NOT NULL,
    data_pagamento date,
    data_com       date,
    ativo_id       UUID         NOT NULL,
    usuario_id     UUID,

    CONSTRAINT pk_provento PRIMARY KEY (id),

    CONSTRAINT provento_ativo_fk
        FOREIGN KEY (ativo_id) REFERENCES ativo (id),

    CONSTRAINT provento_usuario_fk
        FOREIGN KEY (usuario_id) REFERENCES usuario (id)
);