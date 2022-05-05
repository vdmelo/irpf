package br.com.batmelo.finance.infra.hibernate.type.postgresql;

import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;

import java.util.Comparator;
import java.util.UUID;
import java.util.function.Function;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

/**
 * Hibernate type descriptor for a {@link UUIDWrapper} subtype.
 *
 * @param <K> the identifier type.
 * @see PostgresUUIDWrapperType
 */
public class UUIDWrapperTypeDescriptor<K extends UUIDWrapper> extends AbstractTypeDescriptor<K> {

    private static final long serialVersionUID = 8625043768156425743L;

    private final transient Function<UUID, K> factory;

    private transient Comparator<K> comaparator;

    public UUIDWrapperTypeDescriptor(Class<K> type, Function<UUID, K> factory) {
        super(type);
        this.factory = requireNonNull(factory, "factory must not be null.");
    }

    @Override
    public String toString(K value) {
        return value.asString();
    }

    @Override
    public K fromString(String string) {
        return factory.apply(UUIDTypeDescriptor.INSTANCE.fromString(string));
    }

    @Override
    public <X> X unwrap(K value, Class<X> type, WrapperOptions options) {

        if (isNull(value))
            return null;

        return UUIDTypeDescriptor.INSTANCE.unwrap(value.asUUID(), type, options);
    }

    @Override
    public <X> K wrap(X value, WrapperOptions options) {

        if (isNull(value))
            return null;

        return factory.apply(UUIDTypeDescriptor.INSTANCE.wrap(value, options));
    }

    @Override
    public Comparator<K> getComparator() {
        if (isNull(comaparator)) {
            comaparator = Comparator.comparing(UUIDWrapper::asUUID, (v1, v2) -> v2.compareTo(v1));
        }
        return comaparator;
    }

}