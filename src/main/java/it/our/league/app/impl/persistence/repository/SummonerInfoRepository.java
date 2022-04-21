package it.our.league.app.impl.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;

public interface SummonerInfoRepository extends CrudRepository<SummonerInfoJPA, Long> {

    public SummonerInfoJPA findByPuuid(String puuid);

    public SummonerInfoJPA findByGameName(String gameName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SummonerInfoJPA s SET s.summonerLevel = :summonerLevel, s.summonerIconId = :summonerIconId WHERE s.id = :summInfoId")
    public void updateSummonerLvlAndIcon(Integer summInfoId, Integer summonerLevel, Integer summonerIconId);
    
    @Query(value = "SELECT puuid FROM summoner_info", nativeQuery = true)
    public List<String> getAllPuiid();

}
