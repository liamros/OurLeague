package it.kekw.clowngg.match.impl.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;
import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA.PrimaryKeys;

public interface RankInfoRepository extends CrudRepository<RankInfoJPA, PrimaryKeys> {

    public List<RankInfoJPA> findBySummInfoId(Integer summInfoId);

}
