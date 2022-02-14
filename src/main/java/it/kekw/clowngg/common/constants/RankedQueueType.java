package it.kekw.clowngg.common.constants;

public enum RankedQueueType {
    UNKNOWN(0),
    RANKED_SOLO_5x5(1),
    RANKED_FLEX_SR(2);

    private final Integer id;

    RankedQueueType(Integer id) {
        this.id = id;
    }
 

    public Integer id() {return id;}

    public static RankedQueueType getById(Integer id) {
        for(RankedQueueType e : values()) {
            if(e.id.equals(id)) return e;
        }
        return UNKNOWN;
    }
}
