package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class AtivoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected AtivoId(UUID value) {
        super(value);
    }

    public static AtivoId from(UUID uuid) {
        return new AtivoId(uuid);
    }

    @JsonCreator
    public static AtivoId from(String uuid) {
        return new AtivoId(UUID.fromString(uuid));
    }

    public static AtivoId generate() {
        return new AtivoId(UUID.randomUUID());
    }

    public static class AtivoIdType extends PostgresUUIDWrapperType<AtivoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<AtivoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(AtivoId.class,
                AtivoId::from);

        public AtivoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public AtivoIdNaoEncontradoException notFoundException() {
        return new AtivoId.AtivoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<AtivoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "AtivoIdNaoEncontradoException")
    public static class AtivoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}