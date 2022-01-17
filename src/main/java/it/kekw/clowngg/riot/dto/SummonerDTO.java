package it.kekw.clowngg.riot.dto;

public class SummonerDTO {

    private String puuid;

    private String gameName;

    private String tagLine;

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setGameName(String gamename) {
        this.gameName = gamename;
    }

    public String getGameName() {
        return gameName;
    }

    public void setTagLine(String tagline) {
        this.tagLine = tagline;
    }

    public String getTagLine() {
        return tagLine;
    }

}