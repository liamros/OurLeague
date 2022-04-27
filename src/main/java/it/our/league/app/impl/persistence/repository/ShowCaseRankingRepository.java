package it.our.league.app.impl.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;

public interface ShowCaseRankingRepository extends CrudRepository<ShowCaseRankingJPA, Integer> {

    @Query(value = "SELECT * FROM showcase_ranking WHERE stat_name = ?", nativeQuery = true)
    public List<ShowCaseRankingJPA> findByStatName(String statName);

}
