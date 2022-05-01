package it.our.league.app.impl.persistence.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;
import it.our.league.app.impl.persistence.repository.DetatchRepository;

public class ShowCaseRankingRepositoryImpl implements DetatchRepository<ShowCaseRankingJPA> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void detachEntity(ShowCaseRankingJPA entity) {
        entityManager.detach(entity);
    }

    @Override
    public void detachAllEntities(List<ShowCaseRankingJPA> entities) {
        for (ShowCaseRankingJPA entity : entities) {
            detachEntity(entity);
        }
    }
    
}
