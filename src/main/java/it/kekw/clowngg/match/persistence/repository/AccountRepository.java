package it.kekw.clowngg.match.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import it.kekw.clowngg.match.persistence.jpa.AccountInfoJPA;

public interface AccountRepository extends CrudRepository<AccountInfoJPA, Long> {

    public AccountInfoJPA findByPuuid(String puuid);


}
