package it.our.league.app.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.our.league.riot.dto.MatchDTO;

public interface MatchRepository extends MongoRepository<MatchDTO, String> {
    
}
