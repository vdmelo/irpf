CREATE TABLE usuario
(
    id         uuid      NOT NULL,
    version    SMALLINT  NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),

    constraint usuario_pk primary key (id)
);


insert into usuario (id, version, created_at, updated_at)
values ('e556e769-c828-4bbe-b849-4f514cfb0eee', 0, now(), now())