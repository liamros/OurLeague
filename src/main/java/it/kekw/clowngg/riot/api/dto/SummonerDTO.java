package it.kekw.clowngg.riot.api.dto;

public class SummonerDTO {

    private String puuid;

    private String gamename;

    private String tagline;

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    public String getPuuid() {
        return puuid;
    }

    public void setGamename(String gamename) {
        this.gamename = gamename;
    }

    public String getGamename() {
        return gamename;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTagline() {
        return tagline;
    }

}