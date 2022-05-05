package br.com.batmelo.finance.infra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Transient;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class GenericEntityImpl<I> implements GenericEntity<I> {

    @Transient
    private transient int hashCode = 0;

    @JsonIgnore
    @Version
    @Column(name = "version")
    private int entityVersion;

    @JsonIgnore
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime entityCreatedAt;

    @JsonIgnore
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime entityUpdatedAt;

    @Override
    public boolean isNew() {
        return entityVersion == 0;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0)
            hashCode = Objects.hash(getId());
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (obj == null || !getClass().equals(obj.getClass()))
            return false;

        @SuppressWarnings("unchecked")
        GenericEntity<I> other = (GenericEntity<I>) obj;

        return Objects.equals(getId(), other.getId());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id = " + getId() +
                ", isNew = " + isNew() +
                ", version = " + entityVersion +
                ", created = " + entityCreatedAt +
                ", updated = " + entityUpdatedAt +
                "]";
    }
}
