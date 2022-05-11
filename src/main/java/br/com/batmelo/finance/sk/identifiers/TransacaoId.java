package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class TransacaoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected TransacaoId(UUID value) {
        super(value);
    }

    public static TransacaoId from(UUID uuid) {
        return new TransacaoId(uuid);
    }

    @JsonCreator
    public static TransacaoId from(String uuid) {
        return new TransacaoId(UUID.fromString(uuid));
    }

    public static TransacaoId generate() {
        return new TransacaoId(UUID.randomUUID());
    }

    public static class TransacaoIdType extends PostgresUUIDWrapperType<TransacaoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<TransacaoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(TransacaoId.class,
                TransacaoId::from);

        public TransacaoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public TransacaoIdNaoEncontradoException notFoundException() {
        return new TransacaoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<TransacaoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "NegociacaoIdNaoEncontradoException")
    public static class TransacaoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}