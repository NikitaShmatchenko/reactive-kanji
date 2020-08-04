package org.reactive.kanji.service.mapper.api;

public interface EntityMapper<T, R> {

    T mapFromEntity(R entity);
    R mapToEntity(T model);
}
