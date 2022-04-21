package it.our.league.app.impl.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;

public interface RelSummonerMatchRepository extends CrudRepository<RelSummonerMatchJPA, RelSummonerMatchJPA.PrimaryKeys> {
    
    @Query(
    value= "SELECT max(m.game_creation_time) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id",
    nativeQuery = true)
    public Long getLastMatchCreationTimeBySummoner(long summInfoId);

}
