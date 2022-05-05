package br.com.batmelo.finance.infra.hibernate.type.postgresql;

import br.com.batmelo.finance.infra.hibernate.type.UUIDWrapper;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PostgresUUIDType.PostgresUUIDSqlTypeDescriptor;

/**
 * Hibernate custom type for a {@link UUIDWrapper} subtype. This is needed to be
 * able to use {@link UUIDWrapper}s as primary keys. Even with this, is needed
 * to create one subclass per {@link UUIDWrapper} subtype.
 *
 * @param <K> the identifier type.
 * @see UUIDWrapperTypeDescriptor
 */
public abstract class PostgresUUIDWrapperType<K extends UUIDWrapper> extends AbstractSingleColumnStandardBasicType<K> {

    private static final long serialVersionUID = 4882242389741003990L;

    /**
     * Creates a new {@code PostgresUUIDWrapperType}.
     *
     * @param descriptor the {@link UUIDWrapperTypeDescriptor} for the identifier
     *                   type.
     */
    protected PostgresUUIDWrapperType(UUIDWrapperTypeDescriptor<K> descriptor) {
        super(PostgresUUIDSqlTypeDescriptor.INSTANCE, descriptor);
    }

    @Override
    public String getName() {
        return getJavaTypeDescriptor().getJavaType().getSimpleName();
    }

}