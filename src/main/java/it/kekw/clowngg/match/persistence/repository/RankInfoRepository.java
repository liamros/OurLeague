package it.kekw.clowngg.match.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.persistence.jpa.RankInfoJPA;

public interface RankInfoRepository extends CrudRepository<RankInfoJPA, Long> {
    
}
