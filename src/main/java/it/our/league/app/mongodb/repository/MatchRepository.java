package it.our.league.app.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import it.our.league.riot.dto.Match;

public interface MatchRepository extends MongoRepository<Match, String> {
    
    @Query("{ 'metadata.participants' : { $all: [?0] } }")
    public List<Match> findMatchesByPuuid(String puuid);

    @Query("{ 'metadata.matchId' : ?0 }")
    public List<Match> findMatchesByMatchId(String matchId);

}
