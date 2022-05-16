package it.our.league.app.impl.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.ShowCaseRankingJPA;

public interface ShowCaseRankingRepository extends CrudRepository<ShowCaseRankingJPA, Integer>, DetatchRepository<ShowCaseRankingJPA> {

    @Query(value = "SELECT * FROM showcase_ranking WHERE stat_name = ?", nativeQuery = true)
    public List<ShowCaseRankingJPA> findByStatName(String statName);

    public List<ShowCaseRankingJPA> findAllByOrderByPositionAsc();

    @Modifying
    @Query("update ShowCaseRankingJPA s set s.value = ?2, s.description = ?3 where s.id = ?1")
    public Integer saveExceptPosition(Integer id, Float value, String description);
    
    public List<ShowCaseRankingJPA> findByStatNameAndQueueTypeId(String statName, Integer queueTypeId);

}
