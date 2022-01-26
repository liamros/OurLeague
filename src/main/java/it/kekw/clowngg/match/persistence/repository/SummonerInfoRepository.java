package it.kekw.clowngg.match.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.persistence.jpa.SummonerInfoJPA;

public interface SummonerInfoRepository extends CrudRepository<SummonerInfoJPA, Long> {
    
}
