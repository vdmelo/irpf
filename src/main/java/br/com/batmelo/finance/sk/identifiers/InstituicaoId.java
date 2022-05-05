package br.com.batmelo.finance.sk.identifiers;


import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.PostgresUUIDWrapperType;
import br.com.batmelo.finance.infra.hibernate.type.postgresql.UUIDWrapperTypeDescriptor;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.UUID;
import java.util.function.Function;

public final class InstituicaoId extends UUIDWrapper {

    private static final long serialVersionUID = 1321303180633587208L;

    protected InstituicaoId(UUID value) {
        super(value);
    }

    public static InstituicaoId from(UUID uuid) {
        return new InstituicaoId(uuid);
    }

    @JsonCreator
    public static InstituicaoId from(String uuid) {
        return new InstituicaoId(UUID.fromString(uuid));
    }

    public static InstituicaoId generate() {
        return new InstituicaoId(UUID.randomUUID());
    }

    public static class InstituicaoIdType extends PostgresUUIDWrapperType<InstituicaoId> {

        private static final long serialVersionUID = 7480030201978574250L;
        private static final UUIDWrapperTypeDescriptor<InstituicaoId> TYPE_DESCRIPTOR = new UUIDWrapperTypeDescriptor<>(InstituicaoId.class,
                InstituicaoId::from);

        public InstituicaoIdType() {
            super(TYPE_DESCRIPTOR);
        }
    }

    public InstituicaoIdNaoEncontradoException notFoundException() {
        return new InstituicaoId.InstituicaoIdNaoEncontradoException();
    }

    public void applyExistsConstraint(Function<InstituicaoId, Boolean> constraint) {
        if (!constraint.apply(this))
            throw notFoundException();
    }

    // @ApiNotFound(value = "InstituicaoIdNaoEncontradoException")
    public static class InstituicaoIdNaoEncontradoException extends RuntimeException {

        private static final long serialVersionUID = 2473226008420561753L;

    }
}