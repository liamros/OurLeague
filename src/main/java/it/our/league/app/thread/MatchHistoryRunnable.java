package it.our.league.app.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.impl.persistence.entity.SummonerInfoJPA;
import it.our.league.app.impl.persistence.repository.SummonerInfoRepository;

public class MatchHistoryRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MatchHistoryRunnable.class);

    private static final long RIOT_LIMIT_TIMEOUT = 120000;

    /**
     * Revisit this import, does not feel clean
     */
    @Autowired
    private SummonerInfoRepository summonerInfoRepository;

    @Autowired
    private LeagueMatchManager leagueMatchImpl;

    @Override
    public void run() {

        Iterable<SummonerInfoJPA> summoners = summonerInfoRepository.findAll();
        int count = 0;
        for (SummonerInfoJPA summoner : summoners) {
            count += leagueMatchImpl.updateMatchHistory(summoner.getId());
        }
        LOGGER.info("INFO: Found {} matches", count);
        while (true) {
            try {
                leagueMatchImpl.populateMatchInfo();
                LOGGER.info("INFO: MatchHistory update executed successfully");
                break;
            } catch (Exception e) {
                
                if (e.getCause().getMessage().contains("Too Many Requests")) {
                    try {
                        LOGGER.info("INFO: Reached Riot's request limit, thread sleeping for {}ms", RIOT_LIMIT_TIMEOUT);
                        Thread.sleep(RIOT_LIMIT_TIMEOUT);
                    } catch (InterruptedException e1) {
                        LOGGER.error("ERROR: Error while thread sleeping", e1);
                    }
                } else {
                    LOGGER.error("ERROR: Error during populateMatchInfo", e);
                    break;
                }

            }
        }

    }

}
