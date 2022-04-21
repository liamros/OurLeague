package it.our.league.app.impl.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.MatchInfoJPA;

public interface MatchInfoRepository extends CrudRepository<MatchInfoJPA, String> {
    

    @Query(
    value= "SELECT match_id FROM match_info m WHERE game_creation_time IS NULL",
    nativeQuery = true)
    public List<String> getAllPendingMatches();


}
