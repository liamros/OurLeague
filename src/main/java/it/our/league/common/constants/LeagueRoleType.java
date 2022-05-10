package it.our.league.common.constants;

public enum LeagueRoleType {
    
    TOP("TOP", "TOP"),
    JUNGLE("JUNGLE", "JUNGLE"),
    MID("MID", "MIDDLE"),
    ADC("CARRY", "BOTTOM"),
    SUPPORT("SUPPORT", "UTILITY");

    private final String name;
    private final String riotNaming;

    LeagueRoleType(String name, String riotNaming) {
        this.name = name;
        this.riotNaming = riotNaming;
    }

    public String getName() {return name;}
    public String getRiotNaming() {return riotNaming;}
}
