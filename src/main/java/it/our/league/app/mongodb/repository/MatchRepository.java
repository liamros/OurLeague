package it.our.league.app.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.our.league.riot.dto.Match;

public interface MatchRepository extends MongoRepository<Match, String> {
    
}
