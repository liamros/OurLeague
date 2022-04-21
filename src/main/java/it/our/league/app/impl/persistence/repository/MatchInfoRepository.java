package it.our.league.app.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.MatchInfoJPA;

public interface MatchInfoRepository extends CrudRepository<MatchInfoJPA, String> {
    
}
