package it.kekw.clowngg.match.impl.persistence.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import it.kekw.clowngg.match.impl.persistence.entity.SummonerInfoJPA;

public interface SummonerInfoRepository extends CrudRepository<SummonerInfoJPA, Long> {

    public SummonerInfoJPA findByPuuid(String puuid);

    public SummonerInfoJPA findByGameName(String gameName);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SummonerInfoJPA s SET s.summonerLevel = :summonerLevel, s.summonerIconId = :summonerIconId WHERE s.id = :summInfoId")
    public void updateSummonerLvlAndIcon(Integer summInfoId, Integer summonerLevel, Integer summonerIconId);

}
