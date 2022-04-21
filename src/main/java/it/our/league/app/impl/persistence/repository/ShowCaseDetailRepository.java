package it.our.league.app.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.ShowCaseDetailJPA;

public interface ShowCaseDetailRepository extends CrudRepository<ShowCaseDetailJPA, Long> {

    public ShowCaseDetailJPA findByStatName(String statName);

}
