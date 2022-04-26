package it.our.league.app.thread;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.our.league.app.LeagueMatchManager;
import it.our.league.app.LeagueSummonerManager;
import it.our.league.app.controller.dto.AppSummonerDTO;

/**
 * Runnable which executes updates on Summoners and Matches,
 * using APIs from {@link LeagueSummonerManager} and {@link LeagueMatchManager}.
 * </p>
 * It executes the following operations in order :
 * <ol>
 * <li>Update SummonerInfo</li>
 * <li>Fetches new MatchIds</li>
 * <li>Enriches the Match Data, also by persisting Riot's massive response to
 * MongoDB</li>
 * <li>Checks for any uncomplete relSummonerMatch and enriches them</li>
 * </ol>
 * Due to Riot's rate limits, reached such point the thread sleeps for
 * {@link #riotRateLimit}
 * reached such point, it then carries on with its operations
 * 
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
        LOGGER.info("Updated all summoner information");
        List<AppSummonerDTO> summoners = leagueSummonerImpl.getAllSummoners();
        int count = 0;
        for (AppSummonerDTO summoner : summoners) {
            int games = leagueMatchImpl.updateMatchHistory(summoner);
            LOGGER.info("Found {} new matches for {}", games, summoner.getName());
            count += games;
        }
        LOGGER.info("Found {} total new matches", count);
        while (true) {
            try {
                leagueMatchImpl.populateAllMatchData();
                LOGGER.info("MatchHistory update executed successfully");
                break;
            } catch (Exception e) {
                if (!handleRateLimit(e))
                    throw e;
            }
        }
        while (true) {
            try {
                int updatedRsms = leagueMatchImpl.alignRelSummonerMatches();
                LOGGER.info("Updated {} rel summoner-matches", updatedRsms);
                LOGGER.info("DataRefresh complete");
                break;
            } catch (Exception e) {
                if (!handleRateLimit(e))
                    throw e;
            }
        }
    }

    private boolean handleRateLimit(Exception e) {
        if (e.getCause().getMessage().contains("Too Many Requests")) {
            try {
                LOGGER.info("Reached Riot's request limit, thread sleeping for {}ms", riotRateLimit);
                Thread.sleep(riotRateLimit);
            } catch (InterruptedException e1) {
                LOGGER.error("Error while thread sleeping", e1);
            }
        } else {
            LOGGER.error("Error during populateMatchInfo", e);
            return false;
        }
        return true;
    }

}
