package it.our.league.app.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;

public interface ShowCaseRankingRepository extends CrudRepository<ShowCaseRankingJPA, Long> {

    public ShowCaseRankingJPA findByStatName(String statName);

}
