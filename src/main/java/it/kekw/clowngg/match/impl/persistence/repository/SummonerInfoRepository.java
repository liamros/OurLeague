package it.kekw.clowngg.match.impl.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;

public interface SummonerInfoRepository extends CrudRepository<SummonerInfoJPA, Long> {

    public SummonerInfoJPA findByPuuid(String puuid);

    public SummonerInfoJPA findByGameName(String gameName);

    @Query(value = "SELECT puuid FROM summoner_info", nativeQuery = true)
    public List<String> getAllPuiid();

}
