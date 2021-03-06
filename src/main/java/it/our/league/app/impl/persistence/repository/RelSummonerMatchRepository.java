package it.our.league.app.impl.persistence.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.RelSummonerMatchJPA;

public interface RelSummonerMatchRepository
        extends CrudRepository<RelSummonerMatchJPA, RelSummonerMatchJPA.PrimaryKeys> {
	
	@Query(value = "SELECT * FROM rel_summoner_match WHERE win IS NOT NULL", nativeQuery = true)
	public List<RelSummonerMatchJPA> findAllPopulated();

    @Query(value = "SELECT max(m.game_end_time) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND s.id = ?", nativeQuery = true)
    public Timestamp getLastMatchEndTimeBySummoner(long summInfoId);

    @Query(value = "SELECT max(m.game_end_time) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND s.puuid = ?", nativeQuery = true)
    public Timestamp getLastMatchEndTimeBySummoner(String puuid);

    @Query(value = "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND rel.summ_info_id = ?", nativeQuery = true)
    public Integer getNumberOfMatches(long summInfoId);

    @Query(value = "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND s.puuid = ?", nativeQuery = true)
    public Integer getNumberOfMatches(String puuid);

    @Query(value = "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND s.puuid = ? AND m.game_end_time >= ?", nativeQuery = true)
    public Integer getNumberOfMatches(String puuid, Timestamp fromEndTime);

    @Query(value = "SELECT count(m.match_id) FROM summoner_info s, match_info m, rel_summoner_match rel WHERE s.id = rel.summ_info_id AND m.match_id = rel.match_id AND m.patch IS NULL AND s.puuid = ?", nativeQuery = true)
    public Integer getNumberOfPendingMatches(String puuid);

    public List<RelSummonerMatchJPA> findByMatchId(String matchId);

    @Query(value = "SELECT * FROM rel_summoner_match WHERE match_id = ? AND win IS NULL", nativeQuery = true)
    public List<RelSummonerMatchJPA> findUncompleteByMatchId(String matchId);

    @Query(value = "SELECT * FROM rel_summoner_match WHERE win IS NULL", nativeQuery = true)
    public List<RelSummonerMatchJPA> findAllUncomplete();

    @Query(value = "SELECT rsm.* FROM rel_summoner_match rsm, summoner_info si WHERE rsm.summ_info_id = si.id AND rsm.match_id = ? AND si.puuid = ?", nativeQuery = true)
    public RelSummonerMatchJPA findByMatchIdAndPuuid(String matchId, String puuid);

    @Query(value = "SELECT rsm.* FROM rel_summoner_match rsm, summoner_info si WHERE rsm.summ_info_id = si.id AND si.puuid = ? AND rsm.win IS NOT NULL", nativeQuery = true)
    public List<RelSummonerMatchJPA> findPopulatedByPuuid(String puuid);
    
    @Query(value = "SELECT rsm.* FROM rel_summoner_match rsm, summoner_info si, match_info mi WHERE rsm.summ_info_id = si.id AND mi.match_id = rsm.match_id AND si.puuid = ? AND mi.queue_type_id = ? AND rsm.win IS NOT NULL", nativeQuery = true)
    public List<RelSummonerMatchJPA> findPopulatedByPuuidAndQueueTypeId(String puuid, Integer queueTypeId);

    @Query(value = "SELECT rsm.* FROM rel_summoner_match rsm, summoner_info si WHERE si.ID = rsm.SUMM_INFO_ID AND si.PUUID = ? ORDER BY KILLS DESC LIMIT 1", nativeQuery = true)
    public RelSummonerMatchJPA findHighestKillByPuuid(String puuid);
    
    @Query(value = "SELECT rsm.* FROM rel_summoner_match rsm, summoner_info si, match_info mi WHERE si.ID = rsm.SUMM_INFO_ID AND mi.match_id = rsm.match_id AND si.PUUID = ? AND mi.queue_type_id = ? ORDER BY KILLS DESC LIMIT 1", nativeQuery = true)
    public RelSummonerMatchJPA findHighestKillByPuuidAndQueueTypeId(String puuid, Integer queueTypeId);
}
