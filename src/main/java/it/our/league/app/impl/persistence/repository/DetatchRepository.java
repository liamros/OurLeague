package it.our.league.app.impl.persistence.repository;

import java.util.List;

public interface DetatchRepository<T> {
    
    void detachEntity(T entity);

    void detachAllEntities(List<T> entities);
}
