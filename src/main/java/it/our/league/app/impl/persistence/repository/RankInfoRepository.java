package it.our.league.app.impl.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.our.league.app.impl.persistence.entity.RankInfoJPA;
import it.our.league.app.impl.persistence.entity.RankInfoJPA.PrimaryKeys;

public interface RankInfoRepository extends CrudRepository<RankInfoJPA, PrimaryKeys> {

    public List<RankInfoJPA> findBySummInfoId(Integer summInfoId);

    @Query(
    value= "SELECT * FROM rank_info r WHERE (r.wins /(r.wins + r.losses)) = (SELECT MIN(r1.wins /(r1.wins + r1.losses)) FROM rank_info r1)",
    nativeQuery = true)
    public RankInfoJPA getLowerWinRate();

}
