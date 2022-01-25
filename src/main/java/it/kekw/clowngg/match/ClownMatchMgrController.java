package it.kekw.clowngg.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.kekw.clowngg.riot.dto.SummonerDTO;

@RestController
@RequestMapping("match")
public class ClownMatchMgrController implements ClownMatchMgr {

    @Autowired
    private ClownMatchMgrImpl clownMatchMgrImpl;

    @Override
    @GetMapping("/ping")
    public String ping() {
        return clownMatchMgrImpl.ping();
    }

    @Override
    @PutMapping("/insertSummoner")
    public SummonerDTO insertSummoner(@RequestBody String summonerName) {
        return clownMatchMgrImpl.insertSummoner(summonerName);
    }

    @Override
    @GetMapping("/getGameName")
    public String getGameNameByPuuid(@RequestParam String puuid) {
        return clownMatchMgrImpl.getGameNameByPuuid(puuid);
    }
    
}
