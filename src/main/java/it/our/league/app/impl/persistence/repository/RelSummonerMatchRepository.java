package it.our.league.app.impl.persistence.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;

public interface RelSummonerMatchRepository extends CrudRepository<RelSummonerMatchJPA, RelSummonerMatchJPA.PrimaryKeys> {
    
    @Query(
    value= "SELECT max(m.game_creation_time) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id",
    nativeQuery = true)
    public Timestamp getLastMatchCreationTimeBySummoner(long summInfoId);

    @Query(
    value= "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND rel.summ_info_id = ?",
    nativeQuery = true)
    public Integer getNumberOfMatches(long summInfoId);

    @Query(
    value= "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND rel.puuid = ?",
    nativeQuery = true)
    public Integer getNumberOfMatches(String puuid);

}
