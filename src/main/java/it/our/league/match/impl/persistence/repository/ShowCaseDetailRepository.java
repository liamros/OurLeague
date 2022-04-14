package it.our.league.match.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.our.league.match.impl.persistence.entity.ShowCaseDetailJPA;

public interface ShowCaseDetailRepository extends CrudRepository<ShowCaseDetailJPA, Long> {

    public ShowCaseDetailJPA findByStatName(String statName);

}
