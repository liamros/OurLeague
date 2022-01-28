package it.kekw.clowngg.match.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;

public interface SummonerInfoRepository extends CrudRepository<SummonerInfoJPA, Long> {
    
    public SummonerInfoJPA findByPuuid(String puuid);

    public SummonerInfoJPA findByGameName(String gameName);

}
