package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class OperacaoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected OperacaoId(UUID value) {
        super(value);
    }

    public static OperacaoId from(UUID uuid) {
        return new OperacaoId(uuid);
    }

    @JsonCreator
    public static OperacaoId from(String uuid) {
        return new OperacaoId(UUID.fromString(uuid));
    }

    public static OperacaoId generate() {
        return new OperacaoId(UUID.randomUUID());
    }

    public static class OperacaoIdType extends PostgresUUIDWrapperType<OperacaoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<OperacaoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(OperacaoId.class,
                OperacaoId::from);

        public OperacaoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public OperacaoIdNaoEncontradoException notFoundException() {
        return new OperacaoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<OperacaoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "NegociacaoIdNaoEncontradoException")
    public static class OperacaoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}