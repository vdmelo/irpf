package br.com.batmelo.finance.infra.hibernate.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

public abstract class UUIDWrapper implements Serializable {

    private static final long serialVersionUID = 83020441475773780L;

    @JsonIgnore
    private transient int hash = 0;

    @JsonValue
    private final UUID value;

    @JsonCreator
    protected UUIDWrapper(UUID value) {
        this.value = requireNonNull(value);
    }

    protected UUIDWrapper() {
        this.value = null;
    }

    public String asString() {
        return requireNonNullElse(value, "").toString();
    }

    public UUID asUUID() {
        return value;
    }

    @Override
    public final boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        return Objects.equals(value, ((UUIDWrapper) obj).value);
    }

    @Override
    public final int hashCode() {
        if (hash == 0) {
            hash = Objects.hash(value);
        }
        return hash;
    }

    @Override
    public final String toString() {
        return getClass().getSimpleName() + "[" + asString() + "]";
    }
}
