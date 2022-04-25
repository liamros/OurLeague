package it.our.league.app.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.LeagueSummonerManager;
import it.our.league.riot.dto.Summoner;

/**
 * Runnable which executes updates on Summoners and Matches, 
 * using APIs from {@link LeagueSummonerManager} and {@link LeagueMatchManager}.</p>
 * It executes the following operations in order :
 * <ol>
 *      <li>Update SummonerInfo</li>
 *      <li>Fetches new MatchIds</li>
 *      <li>Enriches the Match Data, also by persisting Riot's massive response to MongoDB</li>
 * </ol>
 * Due to Riot's rate limits, reached such point the thread sleeps for {@link #riotRateLimit}
 * reached such point, it then carries on with its operations
 * @author Liam Rossi
 */
public class DataRefreshRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataRefreshRunnable.class);

    @Autowired
    private Long riotRateLimit;
    @Autowired
    private LeagueSummonerManager leagueSummonerImpl;
    @Autowired
    private LeagueMatchManager leagueMatchImpl;

    @Override
    public void run() {

        leagueSummonerImpl.updateAllSummoners();
        LOGGER.info("INFO: Updated all summoner information");
        Iterable<Summoner> summoners = leagueSummonerImpl.getAllSummoners();
        int count = 0;
        for (Summoner summoner : summoners) {
            int games = leagueMatchImpl.updateMatchHistory(summoner.getPuuid());
            LOGGER.info("INFO: Found {} new matches for {}", games, summoner.getName());
            count += games;
        }
        LOGGER.info("INFO: Found {} total new matches", count);
        while (true) {
            try {
                leagueMatchImpl.populateMatchInfo();
                LOGGER.info("INFO: MatchHistory update executed successfully");
                break;
            } catch (Exception e) {
                
                if (e.getCause().getMessage().contains("Too Many Requests")) {
                    try {
                        LOGGER.info("INFO: Reached Riot's request limit, thread sleeping for {}ms", riotRateLimit);
                        Thread.sleep(riotRateLimit);
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
