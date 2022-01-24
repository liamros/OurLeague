package it.kekw.clowngg.match.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.persistence.jpa.AccountJPA;

public interface AccountRepository extends CrudRepository<AccountJPA, Long> {

    public AccountJPA findByPuuid(String puuid);

}
