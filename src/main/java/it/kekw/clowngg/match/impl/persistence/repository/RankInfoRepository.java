package it.kekw.clowngg.match.impl.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.impl.persistence.entity.RankInfoJPA;

public interface RankInfoRepository extends CrudRepository<RankInfoJPA, Long> {
    
}
