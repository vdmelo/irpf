package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class NegociacaoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected NegociacaoId(UUID value) {
        super(value);
    }

    public static NegociacaoId from(UUID uuid) {
        return new NegociacaoId(uuid);
    }

    @JsonCreator
    public static NegociacaoId from(String uuid) {
        return new NegociacaoId(UUID.fromString(uuid));
    }

    public static NegociacaoId generate() {
        return new NegociacaoId(UUID.randomUUID());
    }

    public static class NegociacaoIdType extends PostgresUUIDWrapperType<NegociacaoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<NegociacaoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(NegociacaoId.class,
                NegociacaoId::from);

        public NegociacaoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public NegociacaoIdNaoEncontradoException notFoundException() {
        return new NegociacaoId.NegociacaoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<NegociacaoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "NegociacaoIdNaoEncontradoException")
    public static class NegociacaoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}