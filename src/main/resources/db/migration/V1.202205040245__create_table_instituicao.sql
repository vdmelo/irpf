CREATE TABLE instituicao
(
    id                 UUID         NOT NULL,
    version            SMALLINT     NOT NULL DEFAULT 0,
    created_at         TIMESTAMP    NOT NULL DEFAULT now(),
    updated_at         TIMESTAMP    NOT NULL DEFAULT now(),
    codigo_instituicao INTEGER,
    nome               VARCHAR(128) NOT NULL,

    CONSTRAINT pk_instituicao PRIMARY KEY (id)
);