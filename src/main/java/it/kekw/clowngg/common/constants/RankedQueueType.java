package it.kekw.clowngg.common.constants;

public enum RankedQueueType {
    UNKNOWN(0, "Unknown"),
    RANKED_SOLO_5x5(420, "Ranked Solo"),
    RANKED_FLEX_SR(440, "Ranked Flex");

    private final Integer id;
    private final String description;

    RankedQueueType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }
 

    public Integer id() {return id;}
    public String description() {return description;}

    public static RankedQueueType getById(Integer id) {
        for(RankedQueueType e : values()) {
            if(e.id.equals(id)) return e;
        }
        return UNKNOWN;
    }
}
