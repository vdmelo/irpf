package br.com.batmelo.finance.infra.model;

public interface GenericEntity<I> {
    I getId();

    boolean isNew();
}
