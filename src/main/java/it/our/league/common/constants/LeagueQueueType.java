package it.our.league.common.constants;

public enum LeagueQueueType {
    UNKNOWN(0, "Unknown"),
    RANKED_SOLO_5x5(420, "Solo/Duo"),
    RANKED_FLEX_SR(440, "Flex"),
    CLASH(700, "Clash");

    private final Integer id;
    private final String description;

    LeagueQueueType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
 

    public Integer id() {return id;}
    public String description() {return description;}

    public static LeagueQueueType getById(Integer id) {
        for(LeagueQueueType e : values()) {
            if(e.id.equals(id)) return e;
        }
        return UNKNOWN;
    }
}
