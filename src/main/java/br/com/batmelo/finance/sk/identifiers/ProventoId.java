package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class ProventoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected ProventoId(UUID value) {
        super(value);
    }

    public static ProventoId from(UUID uuid) {
        return new ProventoId(uuid);
    }

    @JsonCreator
    public static ProventoId from(String uuid) {
        return new ProventoId(UUID.fromString(uuid));
    }

    public static ProventoId generate() {
        return new ProventoId(UUID.randomUUID());
    }

    public static class ProventoIdType extends PostgresUUIDWrapperType<ProventoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<ProventoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(ProventoId.class,
                ProventoId::from);

        public ProventoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public ProventoIdNaoEncontradoException notFoundException() {
        return new ProventoId.ProventoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<ProventoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "ProventoIdNaoEncontradoException")
    public static class ProventoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}