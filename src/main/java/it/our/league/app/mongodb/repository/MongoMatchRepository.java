package it.our.league.app.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import it.our.league.riot.dto.Match;

public interface MongoMatchRepository extends MongoRepository<Match, String> {
    
    @Query("{ 'metadata.participants' : { $all: [?0] } }")
    public List<Match> findMatchesByPuuid(String puuid);

    @Query("{ 'metadata.matchId' : ?0 }")
    public List<Match> findMatchesByMatchId(String matchId);

    @Query(value="{ 'info.participants.puuid' : ?0 }", fields="{'info.participants.$':1}")
    public List<Match> findParticipantsByPuuid(String puuid);

}
