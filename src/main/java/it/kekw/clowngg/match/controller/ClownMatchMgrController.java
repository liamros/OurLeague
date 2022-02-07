package it.kekw.clowngg.match.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.kekw.clowngg.match.ClownMatchMgr;
import it.kekw.clowngg.match.controller.dto.ShowCaseDetailDTO;
import it.kekw.clowngg.match.impl.ClownMatchMgrImpl;
import it.kekw.clowngg.riot.dto.SummonerDTO;

@RestController
// CrossOrigin only for development purposes
@CrossOrigin(origins = "http://localhost:3000")
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

    @Override
    @GetMapping("/getWinRate")
    public List<Float> getWinRateBySummInfoId(Integer summInfoId) {
        return clownMatchMgrImpl.getWinRateBySummInfoId(summInfoId);
    }

    @Override
    @GetMapping("/updateAllRanks")
    public void updateAllRanks() {
        clownMatchMgrImpl.updateAllRanks();
    }

    @Override
    @GetMapping("/getShowCaseDetails")
    public List<ShowCaseDetailDTO> getShowCaseDetails() {
        return clownMatchMgrImpl.getShowCaseDetails();
    }

    @Override
    @GetMapping("/setShowCaseDetails")
    public void setShowCaseDetails() {
        clownMatchMgrImpl.setShowCaseDetails();
    }

    @Override
    @GetMapping(value = "/getProfileIcon", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getProfileIconImage(@RequestParam String profileIconNumber) throws IOException {
        return clownMatchMgrImpl.getProfileIconImage(profileIconNumber);
    }

}
