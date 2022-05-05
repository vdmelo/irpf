package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class UsuarioId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected UsuarioId(UUID value) {
        super(value);
    }

    public static UsuarioId from(UUID uuid) {
        return new UsuarioId(uuid);
    }

    @JsonCreator
    public static UsuarioId from(String uuid) {
        return new UsuarioId(UUID.fromString(uuid));
    }

    public static UsuarioId generate() {
        return new UsuarioId(UUID.randomUUID());
    }

    public static class UsuarioIdType extends PostgresUUIDWrapperType<UsuarioId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<UsuarioId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(UsuarioId.class,
                UsuarioId::from);

        public UsuarioIdType() {
            super(TYPE_DESCRIPTOR);
        }

    }

    public UsuarioId.UsuarioIdNaoEncontradoException notFoundException() {
        return new UsuarioId.UsuarioIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<UsuarioId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "UsuarioIdNaoEncontradoException")
    public static class UsuarioIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;
    }
}